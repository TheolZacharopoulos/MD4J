package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Klass;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

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

                String methodName = method.getName();
                String klassName = _schemaClass.getSimpleName();

                if (methodName.equals("name")) {
                    return klassName;
                }

                if (methodName.equals("fields")) {
                    Set<Field> fields = new HashSet<>();

                    for (Method declaredFieldMethod : _schemaClass.getMethods()) {
                        Field field = FieldFactory.make(
                                declaredFieldMethod, _schemaClass);

                        fields.add(field);
                    }
                    return fields;
                }

                if (methodName.equals("supers")) {
                    Klass superKlass = KlassFactory.make(_schemaClass.getSuperclass());
                    return new HashSet<>()
                            .add(superKlass);
                }

                if (methodName.equals("subs")) {
                    // TODO Should I check every Class in the classpath if it is subclass of this class and add them to a Set??
                    return new HashSet<>();
                }

                if (method.getName().equals("schema")) {
                    return SchemaFactory.make(_schemaClass);
                }

                /**
                 * Implement for the Set
                 */
                if (methodName.equals("hashCode")) {
                    return klassName.hashCode();
                }

                return null;
            }
        );
    }

}
