package com.cwi.managed_data.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.factories.KlassFactory;
import com.cwi.managed_data.managed_objects.InitManagedObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class InitializationFactory extends GenericFactory implements InvocationHandler {

    public static <T> T newFactory(Class _schemaFactoryClass) {
        return (T) Proxy.newProxyInstance(
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

        // Get the type which the factory returns (the Schema Class)
        Class schemaClass = method.getReturnType();

        // Create the schema Klass based on the schema.
        Klass schemaKlass = KlassFactory.make(schemaClass);

        HashMap<String, Object> values = initializeValues(schemaClass, args);

        // Create a new proxied object of the returned type,
        // with invocation handler the given dataManager
        return Proxy.newProxyInstance(
                schemaClass.getClassLoader(),
                new Class<?>[]{schemaClass},
                new InitManagedObject(schemaKlass, values));
    }
}
