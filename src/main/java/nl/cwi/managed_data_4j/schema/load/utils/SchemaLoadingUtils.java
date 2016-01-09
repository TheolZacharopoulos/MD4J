package nl.cwi.managed_data_4j.schema.load.utils;

import nl.cwi.managed_data_4j.schema.load.load_impl.FieldImpl;
import nl.cwi.managed_data_4j.schema.load.load_impl.KlassImpl;
import nl.cwi.managed_data_4j.schema.load.load_impl.TypeFactory;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchemaLoadingUtils {


    public static Set<Type> buildTypesFromSchemaKlassesDef(Schema schema, Class<?>... schemaKlassesDefinition) {
        Set<Type> types = new HashSet<>();

        // for each klass definition
        for (Class<?> schemaKlassDefinition : schemaKlassesDefinition) {
            final String klassName = schemaKlassDefinition.getSimpleName();

            // build the fields from the class definition
            final Set<Field> fields = buildFieldsFromSchemaKlassDef(schema, schemaKlassDefinition);

            // create a new klass
            // TODO: complete this (supers, subs)
            final Klass klass = new KlassImpl(klassName, schema, Collections.emptySet(), Collections.emptySet(), fields);

            // wire the owner klass in fields
            fields.forEach(field -> ((FieldImpl) field).setOwner(klass));

            // add the a new klass
            types.add(klass);
        }
        return types;
    }

    public static Set<Field> buildFieldsFromSchemaKlassDef(Schema schema, Class<?> schemaKlassDefinition) {
        Set<Field> fields = new HashSet<>();

        // for each field definition
        for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
            final String fieldName = schemaKlassField.getName();
            final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

            // TODO: Do this recursively (with Primitives as base case)
            final Type fieldType = TypeFactory.getTypeFromClass(fieldReturnClass, schema);

            // check for inverse
            final Field inverse = buildInverse(schemaKlassField);

            // check for many
            final boolean many = buildMany(fieldReturnClass);

            // check for optional
            final boolean optional = buildOptional(fieldReturnClass);

            // add its fields
            // TODO: complete this (many, optional, inverse)
            final Field field = new FieldImpl(fieldName, schema, null, fieldType, many, optional, inverse);
            fields.add(field);
        }
        return fields;
    }

    public static Field buildInverse(Method schemaKlassField) {
        Field inverse = null;
        if (schemaKlassField.isAnnotationPresent(Inverse.class)) {
            final Inverse fieldInverse = schemaKlassField.getAnnotation(Inverse.class);
            final String inverseOtherName = fieldInverse.other().getSimpleName();
            final String inverseField = fieldInverse.field();
            // TODO: set inverse
        }
        return inverse;
    }

    public static boolean buildMany(Class<?> fieldReturnClass) {
        boolean many = false;
        if (fieldReturnClass.isArray()) many = true;
        if (fieldReturnClass.isAssignableFrom(Iterable.class)) many = true;
        return many;
    }

    public static boolean buildOptional(Class<?> fieldReturnClass) {
        // TODO
        return false;
    }
}
