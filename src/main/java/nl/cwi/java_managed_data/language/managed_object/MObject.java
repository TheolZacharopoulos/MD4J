package nl.cwi.java_managed_data.language.managed_object;

import nl.cwi.java_managed_data.language.managed_object.managed_object_field.MObjectField;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.many.MObjectFieldMany;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.many.MObjectFieldManyList;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.many.MObjectFieldManySet;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.single.MObjectFieldSingleMObj;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.single.MObjectFieldSinglePrimitive;
import nl.cwi.java_managed_data.language.schema.models.definition.Field;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import nl.cwi.java_managed_data.M;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The Managed Object
 * @author Theologos Zacharopoulos
 */
public class MObject implements InvocationHandler, M {

    // the outside object of the managed object (its proxy)
    protected Object thisObject = null;

    public void setProxy(Object proxy) {
        this.thisObject = proxy;
    }

    public Object getProxy() {
        return thisObject;
    }

    @Override
    public Klass schemaKlass(Klass... schemaKlass) {
        if (schemaKlass.length > 0) {
            this.schemaKlass = schemaKlass[0];
        }
        return this.schemaKlass;
    }

    // Store props for the object: <Name, Field>
    protected Map<String, MObjectField> props = new LinkedHashMap<>();

    // Keeps the types (schemaKlass pointer)
    protected Klass schemaKlass;

    /**
     * A managed object.
     *
     * @param schemaKlass the schema klass in which managed object belongs to
     * @param initializers initialization values for the object.
     */
    public MObject(Klass schemaKlass, Object... initializers) {
        this.schemaKlass = schemaKlass;

        // setup fields and properties / set default values.
        this.schemaKlass.fields().forEach(this::safeSetupField);

        // initialize fields with actual values.
        if (initializers != null) {
            this.safeInitializeProps(initializers);
        }
    }

    /**
     * Wrapper to handle exceptions.
     * @param _field the input field.
     */
    private void safeSetupField(Field _field) {
        try {
            this.setupField(_field);
        } catch (InvalidFieldValueException | UnknownTypeException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on field setup");
        }
    }

    /**
     * Create a MObjectField and put it in the object values, according to an input Field
     * @param field the input field.
     * @throws UnknownTypeException in case there is a weird primitive.
     * @throws InvalidFieldValueException in case of wrong value assignment to the field.
     */
    protected void setupField(Field field) throws UnknownTypeException, InvalidFieldValueException {

        if (field.type() == null) {
            throw new UnknownTypeException("Type of field '" + field.name() + "' is NULL");
        }

        if (!field.many()) {

            // if it is a primitive make it a Primitive field, otherwise a reference (managed object)
            if (field.type().schemaKlass().name().equals("Primitive")) {
                this.props.put(field.name(), new MObjectFieldSinglePrimitive(this, field));
            } else {
                this.props.put(field.name(), new MObjectFieldSingleMObj(this, field));
            }
        } else {

            // in case it is a Primitive, then is always a List
            // Sets of Primitives are not supported (yet)
            if (field.type().schemaKlass().name().equals("Primitive")) {
                this.props.put(field.name(), new MObjectFieldManyList(this, field));
            } else {
                final Klass klassType = (Klass) field.type();
                if (klassType.key()!= null) {
                    this.props.put(field.name(), new MObjectFieldManySet(this, field));
                } else {
                    this.props.put(field.name(), new MObjectFieldManyList(this, field));
                }
            }
        }
    }

    /**
     * Wrapper to handle exceptions.
     * @param initializers the initialization values
     */
    private void safeInitializeProps(Object... initializers) {
        try {
            this.initializeProps(initializers);
        } catch (InvalidFieldValueException | NoKeyFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on field initialization");
        }
    }

    /**
     * Initializes the object values.
     * @param initializers the initialization values
     * @throws InvalidFieldValueException in case of wrong type of the initialization value.
     * @throws NoKeyFieldException if no key field found.
     */
    protected void initializeProps(Object... initializers) throws InvalidFieldValueException, NoKeyFieldException {
        final List<Field> fieldList = new LinkedList<>();
        fieldList.addAll(this.schemaKlass.fields());

        for (int i = 0; i < fieldList.size(); i++) {
            if (i < initializers.length) {
                final Field fld = fieldList.get(i);
                this._set(fld.name(), initializers[i]);
            }
        }
    }

    /**
     * Extract the field and return its value
     * @param name the field name
     * @return the fields value.
     * @throws NoSuchFieldError in case there no field with this name.
     */
    public Object _get(String name) throws NoSuchFieldError {
        return _getField(name).get(); // return the field's value
    }

    public MObjectField _getField(String name) throws NoSuchFieldError {
        final MObjectField mObjectField = this.props.get(name);

        // check if the field exists
        if (mObjectField == null) {
            throw new NoSuchFieldError(
                "No field named '" + name + "' in class '" + schemaKlass.name() + "'");
        }
        return mObjectField;
    }

    /**
     * Sets the value of a existing field.
     * @param name the name of the field
     * @param value the value of the field
     * @throws NoSuchFieldError in case there no field with this name.
     * @throws InvalidFieldValueException in case the value is not the right type.
     * @throws NoKeyFieldException if no key field found.
     */
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        final MObjectField mObjectField = this.props.get(name);

        // check if the field exists
        if (mObjectField == null) {
            throw new NoSuchFieldError(
                "No field named'" + name + "' in class '" + schemaKlass.name() + "'");
        }

        // set the fields value
        final Field field = mObjectField.getField();
        if (field.many()) {

            // it's an array since it's many
            Object [] inits = ((Object[]) value);

            // in the case it initialized with a zero size array,
            // then clear the values.
            if (inits.length == 0) {
                ((MObjectFieldMany) mObjectField).clear();
            }

            // in case it is a Primitive, then is always a List
            // Sets of Primitives are not supported (yet)
            if (field.type().schemaKlass().name().equals("Primitive")) {
                ((MObjectFieldManyList) mObjectField).init(new LinkedList<>(Arrays.asList(inits)));
                Arrays.asList(inits).forEach(((MObjectFieldManyList)mObjectField)::add);
            } else {
                final Klass klassType = (Klass) field.type();
                if (klassType.key() != null) {
                    for (Object initValue : Arrays.asList(inits)) {
                        ((MObjectFieldManySet) mObjectField).add(initValue);
                    }
                } else {
                    Arrays.asList(inits).forEach(((MObjectFieldManyList)mObjectField)::add);
                }
            }

        } else {
            mObjectField.init(value);
        }
    }

    /**
     * This mechanism helps to create 'pointcuts' inside the MObjects.
     * Therefore, a data manager can use local methods for pointcut definitions.
     * @param method the default method
     * @param args any arguments of the default method
     * @throws Throwable in case of invocation error.
     */
    protected void invokeLocalMethod(Method method, Object[] args) throws Throwable {
        for (Method objMethod : this.getClass().getMethods()) {
            if (method.getName().equals(objMethod.getName())
                && method.getReturnType().equals(objMethod.getReturnType())){
                objMethod.invoke(this, args);
            }
        }
    }

    /**
     * The default method are forwarded to the InvocationHandler.
     * But we want to call the default implementation in case of existence.
     *
     * Check if the object has defined any default methods in its schema
     * The default method has already been overridden by the proxy and it can't be invoked directly.
     * In this case we invoke the default method with the given args.
     *
     * @param proxy the proxy instance
     * @param method the method that has been called
     * @param args any arguments of the method
     * @return any return values of the method, null if none
     * @throws Throwable in case of error during invocation
     */
    protected Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {

        final Class<?> declaringClass = method.getDeclaringClass();

        // declare MethodHandles.Lookup constructor accessible
        Constructor constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);

        // use the constructor to create a lookup object with PRIVATE access
        constructor.setAccessible(true);

        // create a lookup for the default method
        final MethodHandles.Lookup defaultMethodLookup =
                (MethodHandles.Lookup) constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE);

        // create a method handle that will not check for overridden method (unreflectSpecial)
        // Since it is "special" it will skip the overriding done
        // by the proxying and invoke the default implementation
        return defaultMethodLookup
            .unreflectSpecial(method, declaringClass)
            .bindTo(proxy)
            .invokeWithArguments(args);
    }

    /**
     * Default method invocation in managed object.
     *
     * @param proxy the proxy instance
     * @param method the method that has been called
     * @param args any arguments of the method
     * @return any return values of the method, null if none
     * @throws Throwable in case of error during invocation
     */
    protected Object _callDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        // In the case that a method is defined in the MObject
        // that is equal to the default method that has been called on the proxy
        // then call first the local method.
        invokeLocalMethod(method, args);

        // next, call the default method from the proxied object.
        return invokeDefaultMethod(proxy, method, args);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String fieldName = method.getName();

        // if the method is default, invoke this one
        if (method.isDefault()) {
            return _callDefaultMethod(proxy, method, args);
        }

        // This is a way to execute the "attached" methods of the derived Managed Objects,
        // from the proxied objects. (e.g. point.observe()).
        //
        // In case there is already the method declared
        // (in one of the sub-classes/sub managedObjects),
        // then invoke it dynamically, and return.
        for (Method declaredMethod : this.getClass().getMethods()) {
            if (declaredMethod.getName().equals(fieldName)) {
                return method.invoke(this, args);
            }
        }

        // ================
        // Managed Object
        final MObjectField mObjectField = this.props.get(fieldName);
        final boolean isMany = mObjectField.getField().many();

        // if no args given, then just return the field's value.
        if (args == null) {
            // If is not an assignment, get the value.
            return _get(fieldName); // return the field's value
        }

        boolean isAssignment = false;

        Object fieldArgs = args[0];

        // If it is null and it is not many, then one sets the value to null
        if (fieldArgs == null && !isMany) {
            _set(fieldName, null);
            return null;
        }

        // If it is null and it is not many, then empty it
        if (fieldArgs == null && isMany) {
            _set(fieldName, new Object[0]);
            return null;
        }

        // If there are arguments, then it is considered as assignment.
        if (fieldArgs.getClass().isArray() && ((Object [])fieldArgs).length > 0) {
            isAssignment = true;
        }

        // If it is an assignment, then set the value to the field
        if (isAssignment) {

            // if it has one (1) argument, then means that it is a single field
            // At the same time, check always if the field is not many, for safety
            if (((Object [])fieldArgs).length == 1 && !isMany) {
                _set(fieldName, ((Object [])fieldArgs)[0]);
            } else {
                _set(fieldName, fieldArgs);
            }

            return null;
        }

        // If it is not an assignment, then just return the field's value.
        return _get(fieldName);
    }
}
