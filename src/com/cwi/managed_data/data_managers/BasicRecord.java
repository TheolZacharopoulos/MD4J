package com.cwi.managed_data.data_managers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class BasicRecord implements InvocationHandler {

    protected Map<String, Class> types = new HashMap<String, Class>();
    protected Map<String, Object> values = new HashMap<String, Object>();

    public static Object newInstance(Class _schema) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[]{_schema},
                new BasicRecord(_schema));
    }

    protected BasicRecord(Class _schema) {
        for (Method field : _schema.getMethods()) {
            saveType(field.getName(), field.getReturnType());
            defaultValue(field.getName(), field.getReturnType());
        }
    }
    
    private void saveType(String _name, Class _type) {
        types.put(_name, _type);
    }
    
    private void defaultValue(String _name, Class _type) {
        // TODO: Better way to do this?
        if (_type == Integer.class) {
            values.put(_name, 0);
        } else if (_type == String.class) {
            values.put(_name, "");
        } else if (_type == Double.class) {
            values.put(_name, 0.0);
        } else {
            values.put(_name, null);
        }
    }

    protected void _set(String _name, Object _value) {
        values.put(_name, _value);
    }

    protected Object _get(String _name) {
        return values.get(_name);
    }

    /**
     * Handle all fields.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        Object [] fieldArgs = (Object []) args[0]; // because is varargs

        String fieldName = method.getName();
        boolean isAssignment = false;

        // TODO: Find a better strategy to decide if it is an assignment.
        // If there is an argument then is considered as assignment.
        if (fieldArgs.length > 0) {
            isAssignment = true;
        }

        // If is assignment
        if (isAssignment) {

            // If there is no such type.
            if (!types.containsKey(fieldName)) {
                throw new NoSuchFieldError();
            }

            // If the argument is of the right type.
            if (fieldArgs[0].getClass() != values.get(fieldName).getClass()) {
                throw new IllegalArgumentException();
            }

            _set(fieldName, fieldArgs[0]);

        } else { // If is not assignment, return the value.
            return _get(fieldName);
        }

        return null;
    }
}