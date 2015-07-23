package com.cwi.managed_data.managed_objects.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.factories.proxied.KlassFactory;
import com.cwi.managed_data.managed_objects.ManagedObjectBase;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class GenericFactory {

    /**
     * Creates a new proxied factory of schemas.
     * @param _schemaFactoryClass the Factory Class which creates schema
     * @return a new Factory for a schema, (type erasure)
     */
    public static <T> T newFactory(Class _schemaFactoryClass) {
        return (T) Proxy.newProxyInstance(
                _schemaFactoryClass.getClassLoader(),
                new Class<?>[]{_schemaFactoryClass},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    // Get the type which the factory returns (the Schema Class)
                    Class schemaClass = method.getReturnType();

                    // Create the schema Klass based on the schema.
                    Klass schemaKlass = KlassFactory.make(schemaClass);

                    // Create a proxied managed object
                    return Proxy.newProxyInstance(
                        schemaClass.getClassLoader(),
                        new Class<?>[]{schemaClass},
                        new ManagedObjectBase(schemaKlass)
                    );
                }
        );
    }
}