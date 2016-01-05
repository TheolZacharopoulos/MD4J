package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.examples.geometry.Point;
import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.BoolPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.IntegerPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.ObjectPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.StringPrimitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.reflect.Method;
import java.util.Arrays;
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

        // for each klass definition
        for (Class<?> schemaKlassDefinition : _schemaKlassesDef) {
            Set<Field> fields = new HashSet<>();
            final String klassName = schemaKlassDefinition.getSimpleName();

            // for each field definition
            for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
                final String fieldName = schemaKlassField.getName();
                final String fieldReturnType = schemaKlassField.getReturnType().getSimpleName();

                fields.add(new Field() {

                    @Override
                    public String name(String... name) {
                        return fieldName;
                    }

                    @Override
                    public Type type(Type... type) {
                        Type fieldType = null;

                        switch (fieldReturnType) {
                            case "String":
                                fieldType = new StringPrimitive(null); // TODO
                                break;

                            case "Integer":
                                fieldType = new IntegerPrimitive(null); // TODO
                                break;

                            case "Boolean":
                                fieldType = new BoolPrimitive(null); // TODO
                                break;

                            case "Float":
                                fieldType = new IntegerPrimitive(null); // TODO
                                break;

                            case "Double":
                                fieldType = new IntegerPrimitive(null); // TODO
                                break;

                            case "Object":
                                fieldType = new ObjectPrimitive(null); // TODO
                                break;
                        }

                        return fieldType;
                    }

                    @Override
                    public Boolean many(Boolean... many) {
                        return false; // TODO
                    }

                    @Override
                    public Boolean optional(Boolean... optional) {
                        return false; // TODO
                    }

                    @Override
                    public Field inverse(Field... field) {
                        return null; // TODO
                    }

                    @Override
                    public Klass owner(Klass... owner) {
                        return null; // TODO
                    }
                });
            }

            types.add(new Klass() {
                @Override
                public Set<Field> fields(Field... field) {
                    return fields;
                }

                @Override
                public Set<Klass> supers(Klass... supers) {
                    return null; // TODO
                }

                @Override
                public Set<Klass> subklasses(Klass... subklasses) {
                    return null; // TODO
                }

                @Override
                public Schema schema(Schema... schema) {
                    return null; // TODO
                }

                @Override
                public String name(String... name) {
                    return klassName;
                }
            });
        }

        return new Schema() {
            @Override
            public Set<Class> klassInterfaces(Class... interfaces) {
                return klassInterfaces;
            }

            @Override
            public Set<Type> types(Type... type) {
                return types;
            }
        };
    }
}