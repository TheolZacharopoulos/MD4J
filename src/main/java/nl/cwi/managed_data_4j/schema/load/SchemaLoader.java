package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.utils.ArrayUtils;
import nl.cwi.managed_data_4j.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

public class SchemaLoader {

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

    private static Map<String, Type> typesCache = new LinkedHashMap<>();

    /**
     * Bootstraps the schema schema
     * @return The instance of SchemaSchema
     */
    public static Schema bootLoad() {
        return new BootSchema();
    }

    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param factory the factory which creates the schema
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory factory, Class<?>... schemaKlassesDef) {

        // create an empty schema using the factory, will wire it later
        Schema schema = factory.schema();

        // build the types from the schema klasses definition
        Set<Type> types = buildTypesFromSchemaKlassesDef(factory, schema, schemaKlassesDef);

        // wire the types on schema
        schema.types(types.toArray(new Type[types.size()]));

        // TODO: Add schema.schemaKlass

        return schema;
    }

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

            final Map<String, Field> fieldsForKlass =
                buildFieldsFromSchemaKlassDef(factory, schemaKlassDefinition, allFieldsWithReturnType);

            // create a new klass
            final Klass klass = factory.klass();
            klass.name(klassName);
            klass.schema(schema);
            klass.fields(fieldsForKlass.values().toArray(new Field[fieldsForKlass.values().size()]));

            // wire the owner klass in fields
            for (Field field : fieldsForKlass.values()) {
                field.owner(klass);
            }

            typesCache.put(klass.name(), klass);

            // add the a new klass
            types.put(klass, new TypeWithClass(klass, schemaKlassDefinition));
        }

        wireFieldTypes(factory, schema, allFieldsWithReturnType);
        wireFieldInverse(allFieldsWithReturnType);

        wireKlassSupers(types, typesCache);
        wireKlassSubs(types, typesCache);
        wireKlassClassOf(types, schemaKlassesDefinition);

        typesCache.clear();
        return types.keySet();
    }

    private static void wireFieldTypes(
        SchemaFactory factory,
        Schema schema,
        Map<Field, FieldWithMethod> allFieldsWithReturnType)
    {
        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;
            final Type fieldType = getFieldType(method.getReturnType(), schema, factory);
            field.type(fieldType);
        }
    }

    private static void wireFieldInverse(Map<Field, FieldWithMethod> allFieldsWithReturnType) {
        for (Field field : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(field).method;

            final Field fieldInverseField = buildInverse(method, allFieldsWithReturnType);
            if (fieldInverseField != null) {
                field.inverse(fieldInverseField);
            }
        }
    }

    private static void wireKlassSupers(Map<Type, TypeWithClass> types, Map<String, Type> cache) {
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
    }

    private static void wireKlassSubs(Map<Type, TypeWithClass> types, Map<String, Type> cache) {
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
    }

    public static void wireKlassClassOf(Map<Type, TypeWithClass> types, Class<?>[] schemaKlassesDefinition) {
        for (Type type : types.keySet()) {
            for (Class klassInterface : schemaKlassesDefinition) {
                if (type instanceof Klass) {
                    if (klassInterface.getSimpleName().equals(type.name())) {
                        ((Klass) type).classOf(klassInterface);
                    }
                }
            }
        }
    }

    public static Map<String, Field> buildFieldsFromSchemaKlassDef(
            SchemaFactory factory,
            Class<?> schemaKlassDefinition,
            Map<Field, FieldWithMethod> allFieldsWithReturnType)
    {
        Map<String, Field> fieldsForKlass = new LinkedHashMap<>();

        // ** Issue #1 **
        // The elements in the array returned getMethods(), are not sorted and are not in any particular order.
        // Looks like it is not possible to get them in the definition order, by using reflection.
        // So for now, the order of the methods are alphabetical, and it takes only the primitives in the ordering.
        // That means that a constructor with initialization in the schema factory, should have order arguments
        // in alphabetical order, and include only the primitives.
        final Method[] fields = schemaKlassDefinition.getMethods();
        Arrays.sort(fields, ReflectionUtils.methodsOrderComparator());

        for (Method schemaKlassField : fields) {
            final String fieldName = schemaKlassField.getName();
            final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

            // check for many
            final boolean many = ArrayUtils.isMany(fieldReturnClass);;

            // check for optional
            final boolean optional = schemaKlassField.isAnnotationPresent(nl.cwi.managed_data_4j.schema.models.definition.annotations.Optional.class);

            // check for key
            final boolean key = schemaKlassField.isAnnotationPresent(Key.class);

            // check for contain
            final boolean contain = schemaKlassField.isAnnotationPresent(Contain.class);

            // add its fields, the owner Klass will be added later
            final Field field = factory.field(contain, key, many, fieldName, optional);

            fieldsForKlass.put(fieldName, field);
            allFieldsWithReturnType.put(field, new FieldWithMethod(field, schemaKlassField));
        }
        return fieldsForKlass;
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

    private static Type getFieldType(Class<?> fieldReturnClass, Schema schema, SchemaFactory factory) {
        try {
            return TypeFactory.getTypeFromClass(fieldReturnClass, schema, factory, typesCache);
        } catch (UnknownTypeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Set<Klass> buildSupers(Class<?> schemaKlassDefinition, Map<String, Type> cache) {
        Set<Klass> supers = new LinkedHashSet<>();

        // Add interfaces that implements
        for (Class<?> interfaceImpl : schemaKlassDefinition.getInterfaces()) {
            final Type superKlass = cache.get(interfaceImpl.getSimpleName());
            supers.add((Klass) superKlass);
        }

        // Add superclass if any.
        if (schemaKlassDefinition.getSuperclass() != null) {
            final Type superKlass = cache.get(schemaKlassDefinition.getSuperclass().getSimpleName());
            supers.add((Klass) superKlass);
        }

        return supers.isEmpty() ? Collections.emptySet() : supers;
    }

    private static Set<Klass> buildSubs(Class<?> schemaKlassDefinition, Map<String, Type> cache) {
        Set<Klass> subs = new LinkedHashSet<>();

        for (Type type : cache.values()) {
            if (type instanceof Klass) {

                Klass klass = (Klass) type;
                for (Klass superKlass : klass.supers()) {

                    if (superKlass != null &&
                            superKlass.name() != null &&
                            superKlass.name().equals(schemaKlassDefinition.getSimpleName()))
                    {
                        subs.add(klass);
                    }
                }

            }
        }

        return subs.isEmpty() ? Collections.emptySet() : subs;
    }
}