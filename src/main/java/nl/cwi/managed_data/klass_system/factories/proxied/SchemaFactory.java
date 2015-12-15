package nl.cwi.managed_data.klass_system.factories.proxied;

import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.klass_system.models.Type;
import nl.cwi.managed_data.klass_system.models.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * Converts an interface to a Managed Data Schema.
 */
public class SchemaFactory {

    public static Schema make(Class _schemaClass) {

        return (Schema) Proxy.newProxyInstance(
                Schema.class.getClassLoader(),
                new Class<?>[]{Schema.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    String methodName = method.getName();

                    if (methodName.equals("types")) {

                        Set<Type> types = new HashSet<>();

                        for (Method declaredMethods : _schemaClass.getMethods()) {
                            Class typeClass = declaredMethods.getReturnType();
                            Type typeType = TypeFactory.make(typeClass);

                            types.add(typeType);
                        }

                        return types;
                    }

                    if (methodName.equals("classes")) {
                        // TODO: Why is this a Set?
                        Set<Klass> classes = new HashSet<>();
                        Klass schemaKlass = KlassFactory.make(_schemaClass);
                        classes.add(schemaKlass);
                        return classes;
                    }

                    return null;
                }
        );
    }

}