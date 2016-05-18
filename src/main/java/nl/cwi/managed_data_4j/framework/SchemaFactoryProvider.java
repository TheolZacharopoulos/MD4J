package nl.cwi.managed_data_4j.framework;

import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;

/**
 * Provides with a single managed data SchemaFactory
 * It is used as a helper to build schema factories
 * @author Theologos Zacharopoulos
 */
public class SchemaFactoryProvider {

    private static SchemaFactory schemaFactory = null;
    private static Schema schemaSchema = null;

    public static Schema getSchemaSchema() {

        if (schemaSchema == null) {
            final Schema bootstrapSchema = SchemaLoader.bootLoad();
            final BasicDataManager basicFactory = new BasicDataManager();

            // schema factory made from bootstrapping
            final SchemaFactory bootStrapSchemaFactory = basicFactory.factory(SchemaFactory.class, bootstrapSchema);

            schemaSchema = SchemaLoader.load(
                bootStrapSchemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);
        }
        return schemaSchema;
    }

    public static SchemaFactory getSchemaFactory() {

        if (schemaFactory == null) {

            final Schema realSchemaSchema = getSchemaSchema();
            final BasicDataManager realBasicFactory = new BasicDataManager();

            // schema factory made from managed data
            final SchemaFactory realSchemaFactory = realBasicFactory.factory(SchemaFactory.class, realSchemaSchema);

            schemaFactory = realSchemaFactory;
        }
        return schemaFactory;
    }
}
