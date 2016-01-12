package nl.cwi.managed_data_4j.managed_object;

import nl.cwi.managed_data_4j.data_manager.IFactory;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.*;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.*;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class MObject implements InvocationHandler {

    // Store props for the object: <Name, Field>
    protected Map<String, MObjectField> props = new LinkedHashMap<>();

    // Keeps the types (schemaKlass pointer)
    protected Klass schemaKlass;

    // The data manager that manages this managed object.
    protected IFactory factory;

    /**
     * A managed object.
     *
     * @param schemaKlass the schema klass in which managed object belongs to
     * @param factory the data manager that manages this managed object.
     * @param initializers initialization values for the object.
     */
    public MObject(Klass schemaKlass, IFactory factory, Object... initializers) {
        this.schemaKlass = schemaKlass;
        this.factory = factory;

        if (this.schemaKlass.fields() != null) {

            // setup fields and properties / set default values.
            this.schemaKlass.fields().stream().forEach(this::safeSetupField);

            // initialize fields with actual values.
            if (initializers != null) {
                this.safeInitializeProps(initializers);
            }
        }
    }

    /**
     * Wrapper to handle exceptions.
     */
    private void safeSetupField(Field _field) {
        try {
            this.setupField(_field);
        } catch (InvalidFieldValueException | UnknownPrimitiveTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a MObjectField and put it in the object values, according to an input Field
     * @param field the input field.
     * @throws UnknownPrimitiveTypeException in case there is a weird primitive.
     * @throws InvalidFieldValueException in case of wrong value assignment to the field.
     */
    protected void setupField(Field field) throws UnknownPrimitiveTypeException, InvalidFieldValueException {
        MObjectField prop;

        if (!field.many()) {
            if (field.type() instanceof Primitive) {
                prop = new MObjectFieldPrimitive(this, field);
            } else {
                prop = new MObjectFieldRef(this, field);
            }
        } else {
            prop = new MObjectFieldMany(this, field);
        }

        this.props.put(field.name(), prop);
    }

    /**
     * Wrapper to handle exceptions.
     */
    private void safeInitializeProps(Object... initializers) {
        try {
            this.initializeProps(initializers);
        } catch (InvalidFieldValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the object values.
     * @param initializers the initialization values
     * @throws InvalidFieldValueException in case of wrong type of the initialization value.
     */
    protected void initializeProps(Object... initializers) throws InvalidFieldValueException {
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(this.schemaKlass.fields());

        for (int i = 0; i < fieldList.size(); i++) {
            if (i < initializers.length) {
                final Field fld = fieldList.get(i);
                final MObjectField mObjectField = this.props.get(fld.name());

                if (fld.many()) {
                    // it's an array since it's many
                    Object [] inits = ((Object[]) initializers[i]);
                    mObjectField.init(inits);
                } else {
                    mObjectField.init(initializers[i]);
                }
            }
        }
    }

    /**
     * Extract the field and return its value
     * @param name the field name
     * @return the fields value.
     * @throws NoSuchFieldError in case there no field with this name.
     */
    protected Object _get(String name) throws NoSuchFieldError {
        MObjectField mObjectField = this.props.get(name);
        return mObjectField.get(); // return the field's value
    }

    /**
     * Sets the value of a existing field.
     * @param name the name of the field
     * @param value the value of the field
     * @throws NoSuchFieldError in case there no field with this name.
     * @throws InvalidFieldValueException in case the value is not the right type.
     */
    protected void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException {
        // grab the field
        MObjectField mObjectField = this.props.get(name);

        // check if the field exists
        if (mObjectField == null) {
            throw new NoSuchFieldError("No field with the name " + name + " in class " + schemaKlass.name());
        }

        // set the fields value
        mObjectField.init(value);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String fieldName = method.getName();

        // The default method are forwarded to the InvocationHandler.
        // But we want to call the default implementation in case of existence.
        //
        // Check if the object has defined any default methods in its schema
        // The default method has already been overridden by the proxy and it can't be invoked directly.
        // In this case we invoke the default method with the given args.
        if (method.isDefault()) {
            final Class<?> declaringClass = method.getDeclaringClass();

            // declare MethodHandles.Lookup constructor accessible
            Constructor constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
            // use the constructor to create a lookup object with PRIVATE access
            constructor.setAccessible(true);

            // create a lookup for the default method
            final MethodHandles.Lookup defaultMethodLookup =
                    (MethodHandles.Lookup) constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE);

            // create a method handle that won’t check for overridden method (unreflectSpecial)
            // Since it is "special" it will skip the overriding done
            // by the proxying and invoke the default implementation
            return defaultMethodLookup
                .unreflectSpecial(method, declaringClass)
                .bindTo(proxy)
                .invokeWithArguments();
        }

        // This is a way to execute the "attached" methods of the derived Managed Objects,
        // from the proxied objects. (e.g. point.observe()).
        //
        // In case there is already the method declared
        // (in one of the sub-classes/sub managedObjects),
        // then invoke it dynamically, and return.
        for (Method declaredMethod : this.getClass().getMethods()) {
            if (declaredMethod.getName().equals(fieldName)) {
                method.invoke(this, args);
                return null;
            }
        }

        // ================
        // Managed Object

        // if no args given, just return the field's value
        if (args == null) {
            // If is not an assignment, get the value.
            return _get(fieldName); // return the field's value
        }

        boolean isAssignment = false;

        Object fieldArgs = args[0];

        // If there are arguments, then it is considered as assignment.
        if (fieldArgs.getClass().isArray() && ((Object [])fieldArgs).length > 0) {
            isAssignment = true;
        }

        // If it is an assignment, set the value
        if (isAssignment) {

            // in case have 1 arg means that is a single field
            if (((Object [])fieldArgs).length == 1) {
                _set(fieldName, ((Object [])fieldArgs)[0]);
            } else {
                _set(fieldName, fieldArgs);
            }

            return null;
        }

        // If is not an assignment, get the value.
        return _get(fieldName); // return the field's value
    }

    @Override
    public int hashCode() {
        // TODO: Check for Key

        if (this.props.containsKey("name")) {
            return this._get("name").hashCode();
        }
        return super.hashCode();
    }

    public Klass getSchemaKlass() {
        return schemaKlass;
    }
}
