package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.Schema;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Klass.
 */
public class KlassFactory {

    public static Klass make(Class _schemaClass) {
        return (Klass) Proxy.newProxyInstance(
            Klass.class.getClassLoader(),
            new Class<?>[]{Klass.class},

            // The invocation handler.
            (Object proxy, Method method, Object[] args) -> {

                System.out.println("(KlassFactory) : " + method.getName());

                if (method.getName().equals("fields")) {

                }

                if (method.getName().equals("supers")) {

                }

                if (method.getName().equals("subs")) {

                }

                if (method.getName().equals("schema")) {
                    Schema schema = SchemaFactory.make(_schemaClass);
                }

                return null;
            }
        );
    }

}
