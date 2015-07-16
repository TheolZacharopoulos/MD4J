package com.cwi.managed_data.managed_objects.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.factories.KlassFactory;
import com.cwi.managed_data.managed_objects.InitManagedObject;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class InitializationFactory extends GenericFactory {

    public static <T> T newFactory(Class _schemaFactoryClass) {
        return (T) Proxy.newProxyInstance(
                    _schemaFactoryClass.getClassLoader(),
                    new Class<?>[]{_schemaFactoryClass},

                (Object proxy, Method method, Object[] args) -> {

                    // Get the type which the factory returns (the Schema Class)
                    Class schemaClass = method.getReturnType();

                    // Create the schema Klass based on the schema.
                    Klass schemaKlass = KlassFactory.make(schemaClass);

                    // Initialize values from method arguments.
                    HashMap<String, Object> values = new HashMap<String, Object>();
                    for (int i = 0; i < args.length; i++) {

                        // Get the parameter name from the schema.
                        String parameterName = schemaClass.getMethods()[i].getName();

                        // Get the parameter value from the argument.
                        Object parameterValue = args[i];

                        // Save the value.
                        values.put(parameterName, parameterValue);
                    }

                    // Create a new proxied object of the returned type,
                    // with invocation handler the given dataManager
                    return Proxy.newProxyInstance(
                        schemaClass.getClassLoader(),
                        new Class<?>[]{schemaClass},
                        new InitManagedObject(schemaKlass, values)
                    );

                });
    }
}
