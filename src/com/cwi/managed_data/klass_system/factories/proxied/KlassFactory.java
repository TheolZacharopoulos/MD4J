package com.cwi.managed_data.klass_system.factories.proxied;

import com.cwi.managed_data.klass_system.models.Field;
import com.cwi.managed_data.klass_system.models.Klass;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * Converts an interface to a Managed Data Klass.
 */
public class KlassFactory {

    public static Klass make(Class _klassInterface) {
        return (Klass) Proxy.newProxyInstance(
            Klass.class.getClassLoader(),
            new Class<?>[]{Klass.class},

            // The invocation handler.
            (Object proxy, Method method, Object[] args) -> {

                String methodName = method.getName();
                String klassName = _klassInterface.getSimpleName();

                if (methodName.equals("name")) {
                    return klassName;
                }

                if (methodName.equals("fields")) {
                    Set<Field> fields = new HashSet<>();

                    for (Method declaredFieldMethod : _klassInterface.getMethods()) {
                        Field field = FieldFactory.make(
                                declaredFieldMethod, _klassInterface);

                        fields.add(field);
                    }
                    return fields;
                }

                if (methodName.equals("supers")) {
                    Klass superKlass = KlassFactory.make(_klassInterface.getSuperclass());
                    return new HashSet<>()
                            .add(superKlass);
                }

                if (methodName.equals("subs")) {
                    // TODO Should I check every Class in the classpath if it is subclass of this class and add them to a Set??
                    return new HashSet<>();
                }

                if (method.getName().equals("schema")) {
                    return SchemaFactory.make(_klassInterface);
                }

                if (method.getName().equals("klassInterface")) {
                    return _klassInterface;
                }

                /**
                 * Implement for the Set
                 */
                if (methodName.equals("hashCode")) {
                    return _klassInterface.hashCode();
                }

                return null;
            }
        );
    }

}
