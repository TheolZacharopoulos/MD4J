package nl.cwi.managed_data_4j.klass_system.models.bootstrap;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BootSchema implements Schema {

    @Override
    public Set<Class> klassInterfaces(Class... interfaces) {
        return new HashSet<>(Arrays.asList(
                Schema.class,
                Type.class,
                Klass.class,
                Primitive.class,
                Field.class
        ));
    }

    @Override
    public Set<Type> types(Type... type) {
        return new HashSet<>(Arrays.asList(
                new BootKlass(this),
                new BootPrimitive(this)
        ));
    }
}
