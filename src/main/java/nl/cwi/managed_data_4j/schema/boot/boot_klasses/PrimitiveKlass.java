package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Collections;
import java.util.Set;

public class PrimitiveKlass extends TypeKlass {

    private Schema schema;

    public PrimitiveKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Primitive.class.getSimpleName();
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.singleton(new TypeKlass(schema));
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return Collections.emptySet();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
