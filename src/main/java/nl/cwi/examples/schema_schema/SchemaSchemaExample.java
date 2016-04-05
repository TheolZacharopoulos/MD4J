package nl.cwi.examples.schema_schema;

import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;

public class SchemaSchemaExample {

    public static void main(String[] args) {

        // ================================ Schema Schema ========================================

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        final Schema bootstrapSchema = SchemaLoader.bootLoad();

        final BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.make();

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema =
                SchemaLoader.load(schemaFactory, bootstrapSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

        // =======================
        // Test equality
        final BasicFactory basicFactory2 = new BasicFactory(SchemaFactory.class, realSchemaSchema);
        final SchemaFactory schemaFactory2 = basicFactory2.make();
        final Schema realSchemaSchema2 =
                SchemaLoader.load(schemaFactory2, realSchemaSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

        boolean equal = MObjectUtils.equals(realSchemaSchema, realSchemaSchema2);
        if (equal) {
            System.out.println("* EQUAL *");
        } else {
            System.out.println("Should be equal");
            System.exit(-1);
        }

        System.exit(0);
    }
}