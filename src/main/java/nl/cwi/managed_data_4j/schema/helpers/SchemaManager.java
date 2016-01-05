package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.reflect.Method;
import java.util.Arrays;
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

    // TODO: Need to use the factory, Use the MObjectField???
    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param _factory the factory which creates the schema
     * @param _schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory _factory, Class<?>... _schemaKlassesDef) {
        Set<Type> types = new HashSet<>();
        Set<Class> klassInterfaces = new HashSet<>(Arrays.asList(_schemaKlassesDef));

        // create an empty schema, will wire it later
        SchemaImpl schema = new SchemaImpl(klassInterfaces, types);

        // for each klass definition
        for (Class<?> schemaKlassDefinition : _schemaKlassesDef) {
            Set<Field> fields = new HashSet<>();
            final String klassName = schemaKlassDefinition.getSimpleName();

            // for each field definition
            for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
                final String fieldName = schemaKlassField.getName();
                final String fieldReturnType = schemaKlassField.getReturnType().getSimpleName();
                final Type fieldType = TypeFactory.getTypeFromClassName(fieldReturnType);

                // add it's fields
                // TODO: complete this
                fields.add(new FieldImpl(fieldName, schema, null, fieldType, false, false, null));
            }

            // create a new klass
            // TODO: complete this
            Klass klass = new KlassImpl(klassName, schema, Collections.emptySet(), Collections.emptySet(), fields);

            // wire the owner in fields
            fields.stream()
                .map(FieldImpl.class::cast)
                .forEach(field -> field.setOwner(klass));

            // add a new klass
            // TODO: complete this
            types.add(klass);
        }

        // wire the types on schema
        schema.setTypes(types);

        return schema;
    }
}