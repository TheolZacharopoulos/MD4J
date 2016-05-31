package nl.cwi.examples.schema_schema;

import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.BootSchema;
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
        final Schema bootstrapSchema = new BootSchema();

        final BasicDataManager basicFactory = new BasicDataManager();

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.factory(SchemaFactory.class, bootstrapSchema);

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema =
                SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

        // =======================
        // Test equality
        final BasicDataManager basicFactory2 = new BasicDataManager();
        final SchemaFactory schemaFactory2 = basicFactory2.factory(SchemaFactory.class, realSchemaSchema);
        final Schema realSchemaSchema2 =
                SchemaLoader.load(schemaFactory2, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

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