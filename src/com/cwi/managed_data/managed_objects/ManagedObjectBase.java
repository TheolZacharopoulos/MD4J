package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.Type;
import sun.jvm.hotspot.types.WrongTypeException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ManagedObjectBase implements InvocationHandler {

    protected Map<String, Object> values;
    protected Klass schemaKlass;

    public ManagedObjectBase(Klass _schemaClass) {
        schemaKlass = _schemaClass;
        values = new HashMap<>();

        schemaKlass.fields()
            .forEach(field -> setFieldDefaultValue(field));
    }

    private void setFieldDefaultValue(Field _field) {
        String fieldName = _field.name();
        String typeName = _field.type().name();

        // TODO: Better way to do this???
        if (typeName.equals("Integer")) {
            values.put(fieldName, 0);

        } else if (typeName.equals("String")) {
            values.put(fieldName, "");

        } else if (typeName.equals("Double")) {
            values.put(fieldName, 0.0);

        } else {
            values.put(fieldName, null);
        }
    }

    private Field getFieldByName(String _name) {
        return (schemaKlass.fields()
                .stream()
                .filter(field -> field.name().equals(_name))
                .findFirst()).get();
    }

    private void checkType(Type _fieldType, Object _fieldValue) {
        // TODO
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

        // in case there is already the method declared
        // (in one of the sub-classes/sub managedObjects),
        // then invoke it dynamically, and return.
        // * Something like the: create_methods() in Enso-lang
        for (Method declaredMethod : this.getClass().getMethods()) {
            if (declaredMethod.getName().equals(fieldName)) {
                method.invoke(this, args);
                return null;
            }
        }

        // because is varargs
        Object [] fieldArgs = (Object []) args[0];

        // TODO: Find a better strategy to decide if it is an assignment.
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
