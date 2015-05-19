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
        for (Method typeInSchema : _schema.getMethods()) {
            saveType(typeInSchema.getName(), typeInSchema.getReturnType());
            initializeValue(typeInSchema.getName(), typeInSchema.getReturnType());
        }
    }
    
    private void saveType(String _name, Class _type) {
        types.put(_name, _type);
    }
    
    private void initializeValue(String _name, Class _type) {
        // TODO: Better way to do this?
        if (_type == Integer.class) {
            values.put(_name, 0);
        } else if (_type == String.class) {
            values.put(_name, "");
        } else if (_type == Double.class) {
            values.put(_name, 0.0);
        } else {
            values.put(_name, new Object());
        }
    }

    protected void set(String _name, Object _value) {
        values.put(_name, _value);
    }

    protected Object get(String _name) {
        return values.get(_name);
    }

    /**
     * Handle all fields.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        Object [] methodArgs = (Object []) args[0];
        String methodName = method.getName();
        boolean isAssignment = false;

        // TODO: Find a better strategy to decide if it is an assignment.
        // If there is an argument then is considered as assignment.
        if (methodArgs.length > 0) {
            isAssignment = true;
        }

        // If is assignment
        if (isAssignment) {

            // If there is no such type.
            if (!types.containsKey(methodName)) {
                throw new NoSuchFieldError();
            }

            // If the argument is of the right type.
            if (methodArgs[0].getClass() != values.get(methodName).getClass()) {
                throw new IllegalArgumentException();
            }

            set(methodName, methodArgs[0]);

        } else { // If is not assignment, return the value.
            return get(methodName);
        }

        return null;
    }
}