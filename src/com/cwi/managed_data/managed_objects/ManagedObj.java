package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.Type;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ManagedObj implements InvocationHandler {

    // TODO: Map<Field, Object>
    protected Map<String, Object> values;
    protected Klass schemaKlass;

    public ManagedObj(Klass _schemaClass) {
        this.schemaKlass = _schemaClass;
        this.values = new HashMap<>();

        schemaKlass.fields()
            .forEach(
                field -> {
                    setFieldDefaultValue(field);
                });
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
                .filter(field1 -> field1.name().equals(_name))
                .findFirst()).get();
    }

    private void checkType(Type _fieldType, Object _fieldValue) {
        // TODO:
    }

    protected Object _get(String _name) throws NoSuchFieldError {
        Field field = getFieldByName(_name);
        return values.get(_name);
    }

    protected void _set(String _name, Object _value) {

        Field field = getFieldByName(_name);

        // TODO: Check

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

        Object [] fieldArgs = (Object []) args[0]; // because is varargs

        // TODO: Find a better strategy to decide if it is an assignment.
        // If there is an argument then is considered as assignment.
        if (fieldArgs.length > 0) {
            isAssignment = true;
        }

        // If is assignment
        if (isAssignment) {

            // TODO:
//            // If there is no such filed.
//            if (!types.containsKey(fieldName)) {
//                throw new NoSuchFieldError();
//            }

            // TODO:
//            // If the argument is of the right type.
//            if (fieldArgs[0].getClass() != values.get(fieldName).getClass()) {
//                throw new IllegalArgumentException();
//            }

            _set(fieldName, fieldArgs[0]);

        } else { // If is not assignment, return the value.
            return _get(fieldName);
        }

        return null;
    }
}
