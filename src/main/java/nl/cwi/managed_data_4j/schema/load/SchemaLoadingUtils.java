package nl.cwi.managed_data_4j.schema.load;

import com.sun.istack.internal.Nullable;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;

import java.lang.reflect.Method;
import java.util.*;

public class SchemaLoadingUtils {

    private final static SchemaLoaderCache cache = SchemaLoaderCache.getInstance();

    public static Map<String, Type> buildTypesFromSchemaKlassesDef(
            SchemaFactory factory,
            Schema schema,
            Class<?>... schemaKlassesDefinition)
    {
        Map<String, Type> types = new LinkedHashMap<>();

        // for each klass definition
        for (Class<?> schemaKlassDefinition : schemaKlassesDefinition) {
            final String klassName = schemaKlassDefinition.getSimpleName();

            // build the fields from the class definition
            final Map<String, Field> fields = buildFieldsFromSchemaKlassDef(schema, schemaKlassDefinition, factory);

            final Set<Klass> supers = Collections.emptySet(); //buildSupers(schemaKlassDefinition);
            final Set<Klass> subs = Collections.emptySet(); //buildSubs(schemaKlassDefinition);

            // create a new klass
            final Klass klass = factory.klass(klassName);
            klass.schema(schema);
            klass.supers(supers.toArray(new Klass[supers.size()]));
            klass.subklasses(subs.toArray(new Klass[subs.size()]));
            klass.fields(fields.values().toArray(new Field[fields.size()]));

            cache.addType(klass.name(), klass);

            // wire the owner klass in fields
            for (Field field : fields.values()) {
                field.owner(klass);
            }

            // add the a new klass
            types.put(klassName, klass);
        }
        return types;
    }

    private static Type getFieldType(Class<?> fieldReturnClass, Schema schema, SchemaFactory factory) {

        // First try to load the type from cache
        Type fieldType = cache.getType(fieldReturnClass.getSimpleName());

        // if it's not in cache the build it by the class name.
        if (fieldType == null) {

            try {
                // if it's managed object should be in cache so no need to build it.
                // So this builds only Primitives.
                fieldType = TypeFactory.getTypeFromClass(fieldReturnClass, schema, factory, cache);
            } catch (UnknownPrimitiveTypeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return fieldType;
    }

    private static Map<String, Field> buildFieldsFromSchemaKlassDef(
            Schema schema,
            Class<?> schemaKlassDefinition,
            SchemaFactory factory)
    {
        Map<String, Field> fields = new LinkedHashMap<>();

        // for each field definition
        for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
            final String fieldName = schemaKlassField.getName();
            final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

            final Type fieldType = getFieldType(fieldReturnClass, schema, factory);

            // check for inverse
            final Field inverse = null; //buildInverse(schemaKlassField);

            // check for many
            final boolean many = buildMany(fieldReturnClass);

            // check for optional
            final boolean optional = buildOptional(schemaKlassField);

            // add its fields, the owner Klass will be added later
            final Field field = factory.field(fieldName);
            field.type(fieldType);
            field.many(many);
            field.optional(optional);
            field.inverse(inverse);

            cache.addField(field.name(), field);

            fields.put(fieldName, field);
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
            if (inverseOtherKlass == null) {
                return null;
            }

            // get the inverse field
            final String inverseField = fieldInverse.field();

            // extract the field of the inverse-other klass
            return ((Klass)inverseOtherKlass).fields().stream()
                .filter(field -> field.name().equals(inverseField))
                .findFirst()
                .orElse(null);

        } else {
            return null;
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

        cache.getAllTypes().forEach(type -> {
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
        // support arrays, sets and lists
        if (fieldReturnClass.isArray()) return true;
        if (fieldReturnClass.isAssignableFrom(Set.class)) return true;
        if (fieldReturnClass.isAssignableFrom(List.class)) return true;

        return false;
    }

    private static boolean buildOptional(Method schemaKlassField) {
        return schemaKlassField.isAnnotationPresent(Nullable.class);
    }
}
