package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.load_impl.FieldImpl;
import nl.cwi.managed_data_4j.schema.load.load_impl.KlassImpl;
import nl.cwi.managed_data_4j.schema.load.load_impl.TypeFactory;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
     * @param _factory the factory which creates the schema
     * @param _schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory _factory, Class<?>... _schemaKlassesDef) {
        Set<Type> types = new HashSet<>();
        Set<Class> klassInterfaces = new HashSet<>(Arrays.asList(_schemaKlassesDef));

        // create an empty schema using the factory, will wire it later
        Schema schema = _factory.schema();

        // for each klass definition
        for (Class<?> schemaKlassDefinition : _schemaKlassesDef) {
            Set<Field> fields = new HashSet<>();
            final String klassName = schemaKlassDefinition.getSimpleName();

            // for each field definition
            for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
                final String fieldName = schemaKlassField.getName();
                final Class<?> fieldReturnClass = schemaKlassField.getReturnType();
                final String fieldReturnTypeName = fieldReturnClass.getSimpleName();

                final Type fieldType = TypeFactory.getTypeFromClassName(fieldReturnTypeName, schema);

                // add its fields
                // TODO: complete this (many, optional, inverse)
                Field field = new FieldImpl(fieldName, schema, null, fieldType, false, false, null);
                fields.add(field);
            }

            // create a new klass
            // TODO: complete this (supers, subs)
            Klass klass = new KlassImpl(klassName, schema, Collections.emptySet(), Collections.emptySet(), fields);

            // wire the owner klass in fields
            fields.forEach(field -> ((FieldImpl) field).setOwner(klass));

            // add the a new klass
            types.add(klass);
        }

        // wire the klassInterfaces on schema
        schema.klassInterfaces(klassInterfaces.toArray(new Class[klassInterfaces.size()]));

        // wire the types on schema
        schema.types(types.toArray(new Type[types.size()]));

        return schema;
    }
}