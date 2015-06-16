package com.cwi.managed_data.factories;

import com.cwi.managed_data.managed_objects.InitManagedObject;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class InitializationFactory extends Factory {

    public static Object make(Class _schemaFactoryClass) {
        return Proxy.newProxyInstance(
                _schemaFactoryClass.getClassLoader(),
                new Class<?>[]{_schemaFactoryClass},
                new InitializationFactory());
    }

    private HashMap<String, Object> initializeValues(Class _schema, Object[] args) {
        HashMap<String, Object> values = new HashMap<>();

        for (int i = 0; i < args.length; i++) {

            // Get the parameter name from the schema.
            String parameterName = _schema.getMethods()[i].getName();

            // Get the parameter value from the argument.
            Object parameterValue = args[i];

            // Save the value.
            values.put(parameterName, parameterValue);
        }
        return values;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // Get the type which the factory returns (the schema)
        Class schema = method.getReturnType();

        HashMap<String, Object> values = initializeValues(schema, args);

        // Create a new proxied object of the returned type,
        // with invocation handler the given dataManager
        return Proxy.newProxyInstance(
                schema.getClassLoader(),
                new Class<?>[]{schema},
                new InitManagedObject(schema, values));
    }
}
