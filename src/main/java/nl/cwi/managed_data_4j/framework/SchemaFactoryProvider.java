package nl.cwi.managed_data_4j.framework;

import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
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

    public static SchemaFactory getSchemaFactory() {

        if (schemaFactory == null) {

            final Schema bootstrapSchema = SchemaLoader.bootLoad();
            final BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);

            // schema factory made from bootstrapping
            final SchemaFactory bootStrapSchemaFactory = basicFactory.make();

            final Schema realSchemaSchema =
                    SchemaLoader.load(bootStrapSchemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

            final BasicFactory realBasicFactory = new BasicFactory(SchemaFactory.class, realSchemaSchema);

            // schema factory made from managed data
            final SchemaFactory realSchemaFactory = realBasicFactory.make();

            schemaFactory = realSchemaFactory;
        }
        return schemaFactory;
    }

}
