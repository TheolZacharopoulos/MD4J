package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.*;

import java.util.*;

public class SchemaLoader {

    /**
     * Bootstraps the schema schema
     * @return The instance of SchemaSchema
     */
    public static Schema bootLoad() {
        return new BootSchema();
    }

    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param factory the factory which creates the schema
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory factory, Class<?>... schemaKlassesDef) {

        // init cache
        SchemaLoaderCache.getInstance();

        Set<Class> klassInterfaces = new HashSet<>(Arrays.asList(schemaKlassesDef));

        // create an empty schema using the factory, will wire it later
        Schema schema = factory.schema();

        // build the types from the schema klasses definition
        Set<Type> types = SchemaLoadingUtils.buildTypesFromSchemaKlassesDef(factory, schema, schemaKlassesDef);

        // wire the klassInterfaces on schema
        schema.klassInterfaces(klassInterfaces.toArray(new Class[klassInterfaces.size()]));

        // wire the types on schema
        schema.types(types.toArray(new Type[types.size()]));

        return schema;
    }
}