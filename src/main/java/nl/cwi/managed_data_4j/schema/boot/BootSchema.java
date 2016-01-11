package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses.*;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This schema describes the schema of a schemaSchema (self description / MetaSchema)
 */
public class BootSchema extends AbstractSchema {

    public BootSchema() {
        this(null, null);

        final Schema schemaSchema = this;

        // Define the Schema Schema
        final Klass schemaKlass = new SchemaKlass(schemaSchema);
        final Klass typeKlass = new TypeKlass(schemaSchema);
        final Klass primitiveKlass = new PrimitiveKlass(schemaSchema);
        final Klass klassKlass = new KlassKlass(schemaSchema);
        final Klass fieldKlass = new FieldKlass(schemaSchema);

        this.types = new HashSet<>(Arrays.asList(
                schemaKlass,
                typeKlass,
                primitiveKlass,
                klassKlass,
                fieldKlass
        ));

        this.klassInterfaces = new HashSet<>(Arrays.asList(
                Schema.class,
                Type.class,
                Klass.class,
                Primitive.class,
                Field.class
        ));
    }
    public BootSchema(Set<Type> types, Set<Class> klassInterfaces) {
        super(types, klassInterfaces);
    }
}
