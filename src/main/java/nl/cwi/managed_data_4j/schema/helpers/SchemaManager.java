package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchemaManager {

    /**
     * Bootstraps the schema schema
     * @return The instance of SchemaSchema
     */
    public static Schema bootLoad() {
        return new BootSchema();
    }

    // TODO: Not sure
    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param _factory the factory which creates the schema
     * @param _schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory _factory, Class<?>... _schemaKlassesDef) {
        Set<Type> types = new HashSet<Type>();

        // create an empty schema for reference
        Schema schemaSchema = _factory.schema(_schemaKlassesDef, new Type[]{});

        // for every schema definition
        for (Class<?> def : _schemaKlassesDef) {
            Set<Field> fields = new HashSet<Field>();

            // for every field
            for (Method method : def.getMethods()) {
                String methodName = method.getName();
                Class<?> methodReturnType = method.getReturnType();

                Field field = new FieldImpl(schemaSchema, null, methodName, null, false, false, null);
                fields.add(field);
            }

            // TODO: add primitives
            Klass klass = new KlassImpl(def.getSimpleName(), schemaSchema, Collections.emptySet(), Collections.emptySet(), fields);
            types.add(klass);
            fields.forEach(field -> ((FieldImpl)field).setOwner(klass));
        }
        // set the types to the schema
        schemaSchema.types(types.toArray(new Type[types.size()])[0]);

        // return it
        return schemaSchema;
    }
}