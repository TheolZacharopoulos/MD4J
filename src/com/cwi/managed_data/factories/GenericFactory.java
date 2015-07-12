package com.cwi.managed_data.factories;

import com.cwi.managed_data.klass_system.Schema;
import com.cwi.managed_data.klass_system.factories.SchemaFactory;
import com.cwi.managed_data.managed_objects.ManagedObj;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericFactory {

    @SuppressWarnings("unchecked")
    public static <T> T make(Class _schemaFactoryClass) {
        return (T) Proxy.newProxyInstance(
                _schemaFactoryClass.getClassLoader(),
                new Class<?>[] { _schemaFactoryClass },

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    // Get the type which the factory returns (the schema)
                    Class schemaClass = method.getReturnType();

                    Schema schema = SchemaFactory.make(schemaClass);
                    // TODO: use the schema?

                    return Proxy.newProxyInstance(
                        schemaClass.getClassLoader(),
                        new Class<?>[]{schemaClass},
                        new ManagedObj(schemaClass)
                    );
                }
        );
    }

    public void createMethod()
}