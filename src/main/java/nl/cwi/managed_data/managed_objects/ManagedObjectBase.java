package nl.cwi.managed_data.managed_objects;

import nl.cwi.managed_data.klass_system.models.Field;
import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.klass_system.models.Type;
import nl.cwi.managed_data.klass_system.models.Type;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The Basic Managed Object, all managed objects should inherit from this one.
 */
public class ManagedObjectBase implements InvocationHandler {

    protected Map<String, Object> values;
    protected Klass schemaKlass;

    public ManagedObjectBase(Klass _schemaClass) {
        schemaKlass = _schemaClass;
        values = new HashMap<>();

        // initialize fields to null.
        schemaKlass.fields()
            .forEach(field -> values.put(field.name(), null));
    }

    /**
     * Get a field from the schemaKlass by it's name.
     * @param _name the name of the field.
     * @return the field object.
     */
    private Field getFieldByName(String _name) {
        return (schemaKlass.fields()
                .stream()
                .filter(field -> field.name().equals(_name))
                .findFirst()).get();
    }

    // TODO: Should I check the types manually like this?
    private void checkType(Type _fieldType, Object _fieldValue) {
        if (!_fieldType.name().equals(_fieldValue.getClass().getSimpleName())) {
            throw new IllegalArgumentException();
        }
    }

    private void checkFieldByName(String _name) {
        Field field = getFieldByName(_name);

        if (field == null) {
            throw new NoSuchFieldError("No field with the name " + _name);
        }
    }

    protected Object _get(String _name) throws NoSuchFieldError {
        checkFieldByName(_name);
        return values.get(_name);
    }

    protected void _set(String _name, Object _value) throws NoSuchFieldError {
        Field field = getFieldByName(_name);
        checkFieldByName(_name);
        checkType(field.type(), _value);

        values.put(_name, _value);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        String fieldName = method.getName();
        boolean isAssignment = false;

        // TODO: Check this.
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

        // because is varargs
        Object [] fieldArgs = (Object []) args[0];

        // TODO: Is this the right way to check assignment?
        // If there is an argument then is considered as assignment.
        if (fieldArgs.length > 0) {
            isAssignment = true;
        }

        checkFieldByName(fieldName);
        Field field = getFieldByName(fieldName);

        // If it is an assignment
        if (isAssignment) {

            checkType(field.type(), fieldArgs[0]);

            _set(fieldName, fieldArgs[0]);

        // If is not assignment, return the value.
        } else {
            return _get(fieldName);
        }

        return null;
    }
}
