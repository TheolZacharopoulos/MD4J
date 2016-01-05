package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.boot.boot_klasses.*;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.BoolPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.StringPrimitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This schema describes the schema of a schemaSchema (self description / MetaSchema)
 */
public class BootSchema implements Schema {

    private Set<Type> types;

    public BootSchema() {
        final Schema schemaSchema = this;

        // Define the Schema Schema
        final Klass schemaKlass = new SchemaKlass(schemaSchema);
        final Klass typeKlass = new TypeKlass(schemaSchema);
        final Klass primitiveKlass = new PrimitiveKlass(schemaSchema);
        final Klass klassKlass = new KlassKlass(schemaSchema);
        final Klass fieldKlass = new FieldKlass(schemaSchema);
        final Primitive stringPrimitive = new StringPrimitive(schemaSchema);
        final Primitive boolPrimitive = new BoolPrimitive(schemaSchema);

        types = new HashSet<>(Arrays.asList(
            schemaKlass,
            typeKlass,
            primitiveKlass,
            klassKlass,
            fieldKlass,
            stringPrimitive,
            boolPrimitive
        ));
    }

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
        return this.types;
    }
}
