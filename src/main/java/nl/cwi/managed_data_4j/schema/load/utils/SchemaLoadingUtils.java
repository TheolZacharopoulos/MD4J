package nl.cwi.managed_data_4j.schema.load.utils;

import nl.cwi.managed_data_4j.schema.load.models.*;
import nl.cwi.managed_data_4j.schema.models.definition.*;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchemaLoadingUtils {

    private final static SchemaLoaderCache cache = SchemaLoaderCache.getInstance();

    public static Set<Type> buildTypesFromSchemaKlassesDef(Schema schema, Class<?>... schemaKlassesDefinition) {
        Set<Type> types = new HashSet<>();

        // for each klass definition
        for (Class<?> schemaKlassDefinition : schemaKlassesDefinition) {
            final String klassName = schemaKlassDefinition.getSimpleName();

            // build the fields from the class definition
            final Set<Field> fields = buildFieldsFromSchemaKlassDef(schema, schemaKlassDefinition);

            final Set<Klass> supers = buildSupers(schemaKlassDefinition);
            final Set<Klass> subs = buildSubs(schemaKlassDefinition);

            // create a new klass
            final Klass klass = new LoadKlass(klassName, schema, supers, subs, fields);
            cache.addType(klass.name(), klass);

            // wire the owner klass in fields
            fields.forEach(field -> ((LoadField) field).setOwner(klass));

            // add the a new klass
            types.add(klass);
        }
        return types;
    }

    private static Type getFieldType(Class<?> fieldReturnClass, Schema schema) {

        // First try to load the type from cache
        Type fieldType = cache.getType(fieldReturnClass.getSimpleName());

        // if it's not in cache the build it by the class name.
        if (fieldType == null || fieldType.name().equals(LoadNullType.NAME)) {

            // if it's managed object should be in cache so no need to build it.
            // So this builds only Primitives.
            fieldType = TypeFactory.getTypeFromClass(fieldReturnClass, schema);
        }

        return fieldType;
    }

    private static Set<Field> buildFieldsFromSchemaKlassDef(Schema schema, Class<?> schemaKlassDefinition) {
        Set<Field> fields = new HashSet<>();

        // for each field definition
        for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
            final String fieldName = schemaKlassField.getName();
            final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

            // TODO: Check this
            final Type fieldType = getFieldType(fieldReturnClass, schema);

            // check for inverse
            final Field inverse = buildInverse(schemaKlassField);

            // check for many
            final boolean many = buildMany(fieldReturnClass);

            // check for optional
            final boolean optional = buildOptional(fieldReturnClass);

            // add its fields, the owner Klass will be added later
            final Field field = new LoadField(fieldName, schema, new LoadNullKlass(), fieldType, many, optional, inverse);
            cache.addField(field.name(), field);

            fields.add(field);
        }
        return fields;
    }

    private static Field buildInverse(Method schemaKlassField) {
        // TODO

        // Check if the field has the Inverse annotation
        if (schemaKlassField.isAnnotationPresent(Inverse.class)) {
            final Inverse fieldInverse = schemaKlassField.getAnnotation(Inverse.class);

            // get the inverse-other klass
            final Class<?> inverseOther = fieldInverse.other();
            final String inverseOtherName = inverseOther.getSimpleName();
            final Type inverseOtherKlass = cache.getType(inverseOtherName);

            // In this case the inverseOtherKlass is not in the cache yet
            if (inverseOtherKlass == null || inverseOtherKlass.name().equals(LoadNullKlass.NAME)) {
                return null;
            }

            // get the inverse field
            final String inverseField = fieldInverse.field();

            // extract the field of the inverse-other klass
            return ((Klass)inverseOtherKlass).fields().stream()
                .filter(field -> field.name().equals(inverseField))
                .findFirst()
                .orElse(new LoadNullField());

        } else {
            return new LoadNullField();
        }
    }

    private static Set<Klass> buildSupers(Class<?> schemaKlassDefinition) {
        // TODO
        Set<Klass> supers = new HashSet<>();

        // Add interfaces that implements
        for (Class<?> interfaceImpl : schemaKlassDefinition.getInterfaces()) {
            final Type superKlass = cache.getType(interfaceImpl.getSimpleName());
            supers.add((Klass) superKlass);
        }

        // Add superclass if any.
        if (schemaKlassDefinition.getSuperclass() != null) {
            final Type superKlass = cache.getType(schemaKlassDefinition.getSuperclass().getSimpleName());
            supers.add((Klass) superKlass);
        }

        return supers.isEmpty() ? Collections.emptySet() : supers;
    }

    private static Set<Klass> buildSubs(Class<?> schemaKlassDefinition) {
        // TODO
        Set<Klass> subs = new HashSet<>();

        cache.getAllTypes() .forEach(type -> {
            if (!(type instanceof Klass)) return;

            ((Klass)type).supers().forEach(superKlass -> {
                if (superKlass.name().equals(schemaKlassDefinition.getSimpleName())) {
                    subs.add(superKlass);
                }
            });
        });

        return subs.isEmpty() ? Collections.emptySet() : subs;
    }

    private static boolean buildMany(Class<?> fieldReturnClass) {
        // TODO
        boolean many = false;
        if (fieldReturnClass.isArray()) many = true;
        if (fieldReturnClass.isAssignableFrom(Iterable.class)) many = true;
        return many;
    }

    private static boolean buildOptional(Class<?> fieldReturnClass) {
        // TODO
        return false;
    }
}
