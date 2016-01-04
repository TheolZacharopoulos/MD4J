package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.bootstrap.BootSchema;
import nl.cwi.managed_data_4j.schema.bootstrap.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SchemaManager {

    /**
     * Bootstraps the schema schema
     * @param _schemaSchemaDefinitions a list of the schema definitions for schema. (SchemaSchema definitions)
     * @return The instance of SchemaSchema
     */
    public static Schema bootLoad(Class<?>... _schemaSchemaDefinitions) {
        return new BootSchema();
    }

    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param _factory the factory which creates the schema
     * @param _schemasDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory _factory, Class<?>... _schemasDef) {
        Set<Type> types = new HashSet<>();
        Set<Field> fields = new HashSet<>();

        // create an empty schema
        Schema schema = _factory.schema();

        // for every schema definition
        for (Class<?> schemaDef : _schemasDef) {

            // for every field
            for (Method method : schemaDef.getMethods()) {
                String methodName = method.getName();
                Class<?> methodReturnType = method.getReturnType();

                fields.add(createField(methodName, methodReturnType));
            }

            // TODO: add primitives
            types.add(createKlass(
                schemaDef.getSimpleName(),
                fields,
                new HashSet<>(), // TODO
                new HashSet<>(), // TODO
                schema)
            );
        }

        // set the types to the schema
        schema.types(types.stream().toArray(Type[]::new));

        // return it
        return schema;
    }

    /**
     * Helper method to create fields from methods.
     * @param fieldName the name of the field, taken from the method (method name)
     * @param fieldType the method return type
     * @return a new Field instance.
     */
    private static Field createField(String fieldName, Class<?> fieldType) {
        return new Field() {

            @Override
            public String name(String... name) {
                return fieldName;
            }

            @Override
            public Type type(Type... type) {
                return null;
            }

            @Override
            public Boolean many(Boolean... many) {
                return null;
            }

            @Override
            public Boolean optional(Boolean... optional) {
                return null;
            }

            @Override
            public Field inverse(Field... field) {
                return null;
            }

            @Override
            public Klass owner(Klass... owner) {
                return null;
            }
        };
    }

    /**
     * Helper method to create fields from methods.
     * @param klassName the name of the field, taken from the method (method name)
     * @return a new Field instance.
     */
    private static Klass createKlass(
            String klassName,
            Set<Field> klassFields,
            Set<Klass> klassSupers,
            Set<Klass> klassSubs,
            Schema klassSchema)
    {
        return new Klass() {
            @Override
            public Set<Field> fields(Field... field) {
                return klassFields;
            }

            @Override
            public Set<Klass> supers(Klass... supers) {
                return klassSupers;
            }

            @Override
            public Set<Klass> subklasses(Klass... subklasses) {
                return klassSubs;
            }

            @Override
            public Schema schema(Schema... schema) {
                return klassSchema;
            }

            @Override
            public String name(String... name) {
                return klassName;
            }
        };
    }
}