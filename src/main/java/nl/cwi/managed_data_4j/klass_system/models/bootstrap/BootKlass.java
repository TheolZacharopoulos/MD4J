package nl.cwi.managed_data_4j.klass_system.models.bootstrap;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Field;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Schema;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BootKlass implements Klass {

    private Schema schema;

    public BootKlass() {
        this(null);
    }

    public BootKlass(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return Klass.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field... field) {
        return new HashSet<Field>(Arrays.asList(new BootField(this)));
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return new HashSet<Klass>();
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return new HashSet<Klass>();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }
}
