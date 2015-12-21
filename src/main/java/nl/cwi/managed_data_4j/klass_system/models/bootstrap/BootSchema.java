package nl.cwi.managed_data_4j.klass_system.models.bootstrap;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BootSchema implements Schema {

    @Override
    public Class klassInterface(Class... interfaces) {
        return Klass.class;
    }

    @Override
    public Set<Type> types(Type... type) {
        return new HashSet<>(Arrays.asList(
                new BootKlass(this),
                new BootPrimitive(this)
        ));
    }
}
