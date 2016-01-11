package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses;

import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Collections;
import java.util.Set;

public class PrimitiveKlass extends TypeKlass {

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
}
