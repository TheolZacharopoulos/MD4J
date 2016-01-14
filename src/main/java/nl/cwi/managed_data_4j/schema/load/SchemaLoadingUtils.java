package nl.cwi.managed_data_4j.schema.load;

import com.sun.istack.internal.Nullable;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.utils.ArrayUtils;

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

    private static class TypeWithClass {

        public Type type = null;
        public Class<?> clazz = null;

        public TypeWithClass(Type type, Class<?> clazz) {
            this.type = type;
            this.clazz = clazz;
        }
    }

    private final static SchemaLoaderCache cache = SchemaLoaderCache.getInstance();

    public static Set<Type> buildTypesFromSchemaKlassesDef(
            SchemaFactory factory,
            Schema schema,
            Class<?>... schemaKlassesDefinition)
    {
        Map<Type, TypeWithClass> types = new LinkedHashMap<>();
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
            types.put(klass, new TypeWithClass(klass, schemaKlassDefinition));
        }

        // Wire field types
        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;
            final Type fieldType = getFieldType(method.getReturnType(), schema, factory);
            field.type(fieldType);
        }

        // Wire inverse
        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;

            final Field fieldInverseField = buildInverse(method, allFieldsWithReturnType);
            if (fieldInverseField != null) {
                field.inverse(fieldInverseField);
            }
        }

        // Klass supers
        for (Type type : types.keySet()) {

            if (type instanceof Klass) {
                Klass klass = (Klass) type;
                final TypeWithClass typeWithClass = types.get(klass);

                final Set<Klass> superKlasses = buildSupers(typeWithClass.clazz, cache);
                if (superKlasses.size() > 0) {
                    klass.supers(superKlasses.toArray(new Klass[superKlasses.size()]));
                }
            }
        }

        // Klass subs
        for (Type type : types.keySet()) {
            if (type instanceof Klass) {
                final TypeWithClass typeWithClass = types.get(type);

                final Set<Klass> subKlasses = buildSubs(typeWithClass.clazz, cache);
                if (subKlasses.size() > 0) {
                    final Klass[] subsToArray = subKlasses.toArray(new Klass[subKlasses.size()]);
                    ((Klass) type).subklasses(subsToArray);
                }
            }
        }

//        debugTypes(types.keySet());

        cache.clean();
        return types.keySet();
    }

    private static Field buildInverse(Method method, Map<Field, FieldWithMethod> allFieldsWithReturnType) {
        Field fieldInverseField = null;
        if (method.isAnnotationPresent(Inverse.class)) {
            final Inverse fieldInverse = method.getAnnotation(Inverse.class);

            // get the inverse-other klass
            final Class<?> inverseOther = fieldInverse.other();
            final String inverseOtherName = inverseOther.getSimpleName();

            // get the inverse-field
            final String fieldInverseFieldName = fieldInverse.field();

            // check field name and owner
            for (Field fieldForSearch : allFieldsWithReturnType.keySet()) {
                final String fieldForSearchName = fieldForSearch.name();
                final String fieldForSearchOwnerName = fieldForSearch.owner().name();

                if  (fieldForSearchName.equals(fieldInverseFieldName) &&
                        fieldForSearchOwnerName.equals(inverseOtherName))
                {
                    fieldInverseField = fieldForSearch;
                }
            }
        }
        return fieldInverseField;
    }

    // TODO: Remove this For debugging purposes only
    private static void debugTypes(Set<Type> types) {
        for (Type type : types) {

            if (Klass.class.isAssignableFrom(type.getClass())) {

                Klass klass = (Klass) type;
                System.out.println("*" + klass.name());

                // supers
                if (klass.supers() != null) {
                    for (Klass superKlass : klass.supers()) {
                        System.out.println("  - Super: " + superKlass.name());
                    }
                }

                // subs
                if (klass.subklasses() != null) {
                    for (Klass subKlass : klass.subklasses()) {
                        System.out.println("  - Sub: " + subKlass.name());
                    }
                }

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

                    // many
                    System.out.println("\t\t- Many : " + field.many());

                    // optional
                    System.out.println("\t\t- Optional : " + field.optional());
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

    private static Set<Klass> buildSupers(Class<?> schemaKlassDefinition, SchemaLoaderCache cache) {
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

    private static Set<Klass> buildSubs(Class<?> schemaKlassDefinition, SchemaLoaderCache cache) {
        Set<Klass> subs = new HashSet<>();

        for (Type type : cache.getAllTypes()) {
            if (type instanceof Klass) {

                Klass klass = (Klass) type;
                for (Klass superKlass : klass.supers()) {

                    if (superKlass.name().equals(schemaKlassDefinition.getSimpleName())) {
                        subs.add(klass);
                    }
                }

            }
        }

        return subs.isEmpty() ? Collections.emptySet() : subs;
    }

    private static boolean buildMany(Class<?> fieldReturnClass) {
        return ArrayUtils.isArray(fieldReturnClass);
    }

    private static boolean buildOptional(Method schemaKlassField) {
        return schemaKlassField.isAnnotationPresent(Nullable.class);
    }
}
