package nl.cwi.managed_data.klass_system.factories.proxied;

import nl.cwi.managed_data.klass_system.models.Primitive;
import nl.cwi.managed_data.klass_system.models.Primitive;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PrimitiveFactory {

    public static Primitive make(Class _schemaClass) {
        return (Primitive) Proxy.newProxyInstance(
                Primitive.class.getClassLoader(),
                new Class<?>[]{Primitive.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {
                    return null;
                }
        );
    }
}