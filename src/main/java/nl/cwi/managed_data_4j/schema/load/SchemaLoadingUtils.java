package nl.cwi.managed_data_4j.schema.load;

import com.sun.istack.internal.Nullable;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;

import java.lang.reflect.Method;
import java.util.*;

public class SchemaLoadingUtils {

    private static class FieldWithMethod {

        public Field field = null;
        public Method method = null;

        public FieldWithMethod(Field field, Method method) {
            this.field = field;
            this.method = method;
        }
    }

    private final static SchemaLoaderCache cache = SchemaLoaderCache.getInstance();

    public static Map<String, Type> buildTypesFromSchemaKlassesDef(
            SchemaFactory factory,
            Schema schema,
            Class<?>... schemaKlassesDefinition)
    {
        Map<String, Type> types = new LinkedHashMap<>();
        Map<Field, FieldWithMethod> allFieldsWithReturnType = new LinkedHashMap<>();

        // =================
        // Klasses
        for (Class<?> schemaKlassDefinition : schemaKlassesDefinition) {
            final String klassName = schemaKlassDefinition.getSimpleName();

            // =================
            // Fields
            Map<String, Field> fieldsForKlass = new LinkedHashMap<>();
            for (Method schemaKlassField : schemaKlassDefinition.getMethods()) {
                final String fieldName = schemaKlassField.getName();
                final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

                // check for many
                final boolean many = buildMany(fieldReturnClass);

                // check for optional
                final boolean optional = buildOptional(schemaKlassField);

                // add its fields, the owner Klass will be added later
                final Field field = factory.field(fieldName);
                field.many(many);
                field.optional(optional);

                cache.addField(field.name(), field);

                fieldsForKlass.put(fieldName, field);
                allFieldsWithReturnType.put(field, new FieldWithMethod(field, schemaKlassField));
            }

            // create a new klass
            final Klass klass = factory.klass(klassName);
            klass.schema(schema);
            klass.fields(fieldsForKlass.values().toArray(new Field[fieldsForKlass.values().size()]));


            // wire the owner klass in fields
            for (Field field : fieldsForKlass.values()) {
                field.owner(klass);
            }

            cache.addType(klass.name(), klass);

            // add the a new klass
            types.put(klassName, klass);
        }

        // Wire field types
        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;
            final Type fieldType = getFieldType(method.getReturnType(), schema, factory);
            field.type(fieldType);
        }

        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;

            // Check if the field has the Inverse annotation
            if (method.isAnnotationPresent(Inverse.class)) {
                final Inverse fieldInverse = method.getAnnotation(Inverse.class);

                // get the inverse-other klass
                final Class<?> inverseOther = fieldInverse.other();
                final String inverseOtherName = inverseOther.getSimpleName();

                // get the inverse-field
                final String fieldInverseFieldName = fieldInverse.field();

                // check field name and owner
                Field fieldInverseField = null;
                for (Field fieldForSearch : allFieldsWithReturnType.keySet()) {
                    final String fieldForSearchName = fieldForSearch.name();
                    final String fieldForSearchOwnerName = fieldForSearch.owner().name();

                    if  (fieldForSearchName.equals(fieldInverseFieldName) &&
                        fieldForSearchOwnerName.equals(inverseOtherName))
                    {
                        fieldInverseField = fieldForSearch;
                    }
                }

                // set the inverse
                if (fieldInverseField != null) {
                    field.inverse(fieldInverseField);
                }
            }
        }

        // TODO: Klass supers
        // TODO: Klass subs

        cache.clean();
        return types;
    }

    // TODO: Remove this For debugging purposes only
    private static void debugTypes(Map<String, Type> types) {
        for (Type type : types.values()) {
            if (Klass.class.isAssignableFrom(type.getClass())) {
                Klass klass = (Klass) type;
                System.out.println("*" + klass.name());
                for (Field field : klass.fields()) {
                    System.out.println("\t" + field.name());

                    // type
                    if (field.type() == null) {
                        System.out.println("\t\t- Type : <<NULL>>");
                    } else {
                        System.out.println("\t\t- Type : " + field.type().name());
                    }

                    // owner
                    if (field.owner() == null) {
                        System.out.println("\t\t- Owner : <<NULL>>");
                    } else {
                        System.out.println("\t\t- Owner : " + field.owner().name());
                    }

                    // inverse
                    if (field.inverse() != null) {
                        System.out.println("\t\t- Inverse : " + field.inverse().name());
                    }
                }
            }
        }
    }

    private static Type getFieldType(Class<?> fieldReturnClass, Schema schema, SchemaFactory factory) {
        try {
            return TypeFactory.getTypeFromClass(fieldReturnClass, schema, factory, cache);
        } catch (UnknownPrimitiveTypeException e) {
            throw new RuntimeException(e.getMessage());
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
