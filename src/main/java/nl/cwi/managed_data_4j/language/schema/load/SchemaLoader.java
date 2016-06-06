package nl.cwi.managed_data_4j.language.schema.load;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.primitives.PrimitivesManager;
import nl.cwi.managed_data_4j.language.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;

/**
 * Dynamically loads schemas.
 * This is where the whole loading process is done,
 * given Schema definition (interface), this class provides an instance
 * of this schema.
 *
 * @author Theologos Zacharopoulos
 */
public class SchemaLoader {

    private static final PrimitivesManager primitiveManager = PrimitivesManager.getInstance();

    /**
     * Helper private class to keep Fields with their Method which define them.
     */
    private static final class FieldWithMethod {

        public Field field = null;
        public Method method = null;

        public FieldWithMethod(Field field, Method method) {
            this.field = field;
            this.method = method;
        }
    }

    /**
     * Helper private class to keep Types with their Classes which define them.
     */
    private static final class TypeWithClass {

        public Type type = null;
        public Class<?> clazz = null;

        public TypeWithClass(Type type, Class<?> clazz) {
            this.type = type;
            this.clazz = clazz;
        }
    }

    // helper cache for the loading process.
    private static final Map<String, Type> typesCache = new LinkedHashMap<>();

    /**
     * Bootstraps the schema schema
     * @return The instance of SchemaSchema
     */
    public static Schema bootLoad() {
        return new BootSchema();
    }

    /**
     * This is a special case.
     * Since every Managed data has a schemaKlass,
     * which means that during the loading of managed objects (e.g. Point),
     * we need an already created Klass that would used in the schemaKlass field definition.
     *
     * However, if the Klass schema definition has not been given as input in schemaKlassesDef,
     * then the schema loader will never created, and we will have an error.
     * Thus, we check if the Klass definition is given in schemaKlassesDef, and if not, then
     * we can the Klass definition from the schemaSchema itself.
     *
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     */
    private static void setupCacheForSchemaKlass(SchemaFactory schemaFactory, List<Class<?>> schemaKlassesDef) {
        boolean includesSchemaDef    = schemaKlassesDef.stream().anyMatch(aClass -> aClass.getSimpleName().equals("Schema"));
        boolean includesPrimitiveDef = schemaKlassesDef.stream().anyMatch(aClass -> aClass.getSimpleName().equals("Primitive"));
        boolean includesKlassDef     = schemaKlassesDef.stream().anyMatch(aClass -> aClass.getSimpleName().equals("Klass"));
        boolean includesFieldDef     = schemaKlassesDef.stream().anyMatch(aClass -> aClass.getSimpleName().equals("Field"));

        if (!includesSchemaDef) {
            typesCache.put("Schema", schemaFactory.Schema().schemaKlass());
        }

        if (!includesPrimitiveDef) {
            typesCache.put("Primitive", schemaFactory.Primitive().schemaKlass());
        }

        if (!includesKlassDef) {
            typesCache.put("Klass", schemaFactory.Klass().schemaKlass());
        }

        if (!includesFieldDef) {
            typesCache.put("Field", schemaFactory.Field().schemaKlass());
        }
    }

    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param factory the factory which creates the schema
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory factory, Class<?>... schemaKlassesDef) {

        // System.out.println("SchemaFactory: create schema");

        // Filter out primitives by loading them separately
        final List<Class<?>> schemaKlasses = new LinkedList<>();
        for (Class<?> schemaClass : schemaKlassesDef) {
            if (Primitives.class.isAssignableFrom(schemaClass)) {
                primitiveManager.loadPrimitives(schemaClass);
            } else {
                schemaKlasses.add(schemaClass);
            }
        }

        // setup the cache for classes
        setupCacheForSchemaKlass(factory, schemaKlasses);

        // create an empty schema using the factory, will wire it later
        final Schema schema = factory.Schema();

        // build the types from the schema klasses definition
        final Set<Type> types = buildTypesFromClasses(factory, schema, schemaKlasses);

        // wire the types on schema
        // it is inverse so it will refer to schema.types() directly
        types.forEach(type -> type.schema(schema));

        return schema;
    }

    private static void wireSchemaKlasses(Schema schemaSchema) {

        // get the primitive's schemaKlass
        final Klass primitiveSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Primitive"))
                .findFirst().orElse(null);

        // wire the primitive's schemaKlass
        for (Primitive primitive : schemaSchema.primitives()) {
            primitive.schemaKlass(primitiveSchemaKlass);
        }

        // get the klass's schemaKlass
        final Klass klassSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Klass"))
                .findFirst().orElse(null);

        // wire the klasses's schemaKlass
        for (Klass klass : schemaSchema.klasses()) {
            klass.schemaKlass(klassSchemaKlass);
        }

        // get the field's schemaKlass
        final Klass fieldSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Field"))
                .findFirst().orElse(null);

        // wire the field's schemaKlass
        for (Klass klass : schemaSchema.klasses()) {
            for (Field field : klass.fields()) {
                field.schemaKlass(fieldSchemaKlass);
            }
        }
    }

    private static Set<Type> buildTypesFromClasses(
            SchemaFactory factory,
            Schema schema,
            List<Class<?>> schemaKlassesDefinition)
    {
        final Map<Type, TypeWithClass> types = new LinkedHashMap<>();

        // <classNameFieldNameCombo, FieldWithMethod>
        // since the klass of the field has not been wired we need another way to
        // use as key the all the fields in the map.
        // hashcode can not use yet the klass of the field (not has been set)
        // however, we can use klass name for that.
        final Map<String, FieldWithMethod> allFieldsWithReturnType = new LinkedHashMap<>();

        // =================
        // Klasses
        for (Class<?> schemaKlassDefinition : schemaKlassesDefinition) {
            final String klassName = schemaKlassDefinition.getSimpleName();

            // System.out.println("> SchemaFactory: create klass " + klassName);
            final Map<String, Field> fieldsForKlass =
                buildFieldsFromMethods(klassName, factory, schemaKlassDefinition, allFieldsWithReturnType);

            // create a new klass
            final Klass klass = factory.Klass();
            klass.name(klassName);
            klass.schema(schema);

            // wire the owner klass in fields,
            // it is inverse so it will refer to klass.fields() directly
            fieldsForKlass.values().forEach(field -> field.owner(klass));

            typesCache.put(klass.name(), klass);

            // add the a new klass
            types.put(klass, new TypeWithClass(klass, schemaKlassDefinition));
        }

        wireFieldTypes(factory, schema, allFieldsWithReturnType);
        wireFieldInverse(allFieldsWithReturnType);
        wireFieldTypeKeys(types);

        wireSchemaKlasses(schema.schemaKlass().schema());

        wireKlassSupers(types, typesCache);
        wireKlassSubs(types, typesCache);
        wireKlassClassOf(types, schemaKlassesDefinition);

        typesCache.clear();
        return types.keySet();
    }

    /**
     * From all the created types that are type of Klass,
     * get their field and set as key the first field found as KEY.
     *
     * We dont need that for primitives since it is done during the type assignment.
     * @param types all the created types
     */
    private static void wireFieldTypeKeys(Map<Type, TypeWithClass> types) {
        for (Type type : types.keySet()) {
            Field key = null;

            if (!type.schemaKlass().name().equals("Klass")) continue;

            for (Field field : ((Klass) type).fields()) {
                if (field.key()) {
                    key = field;
                    break;
                }
            }

            if (key != null) {
                // System.out.println(" > Wire Key for " + type.name() + " = " + key.name());
            }
            ((Klass) type).key(key);
        }
    }

    /**
     * Build fields from given methods
     * @param klassName the name of the klass that the fields are belong to
     * @param factory the schema factory
     * @param schemaKlassDefinition the Class of the klass
     * @param allFieldsWithReturnType all created fields
     * @return all the fields of a klass.
     */
    public static Map<String, Field> buildFieldsFromMethods(
            String klassName,
            SchemaFactory factory,
            Class<?> schemaKlassDefinition,
            Map<String, FieldWithMethod> allFieldsWithReturnType)
    {
        final Map<String, Field> fieldsForKlass = new LinkedHashMap<>();

        // The elements in the array returned getMethods(), are not sorted and are not in any particular order.
        // Looks like it is not possible to get them in the definition order, by using reflection.
        // So for now, the order of the methods are alphabetical, and it takes only the primitives in the ordering.
        // That means that a constructor with initialization in the schema factory, should have order arguments
        // in alphabetical order, and include only the primitives.
        final Method[] fields = schemaKlassDefinition.getMethods();
        Arrays.sort(fields, (Method o1, Method o2) -> {

            // Order only the primitives and non-many,
            // the rest put them at the end.
            boolean isM1Comparable = ((!primitiveManager.isMany(o1.getReturnType())) && primitiveManager.isPrimitiveClass(o1.getReturnType()));
            boolean isM2Comparable = ((!primitiveManager.isMany(o2.getReturnType())) && primitiveManager.isPrimitiveClass(o2.getReturnType()));

            if ((!isM1Comparable) && (!isM2Comparable)) return 0;
            if (!isM1Comparable) return 1;
            if (!isM2Comparable) return -1;
            return o1.getName().compareTo(o2.getName());
        });

        for (Method schemaKlassField : fields) {
            final String fieldName = schemaKlassField.getName();
            final Class<?> fieldReturnClass = schemaKlassField.getReturnType();

            if (schemaKlassField.isDefault()) {
                // System.out.println("  > SchemaFactory: DEFAULT field " + fieldName + " <" + fieldReturnClass.getSimpleName() + "> - SKIP");
                continue;
            }

            // System.out.println("  > SchemaFactory: create field " + fieldName + " <" + fieldReturnClass.getSimpleName() + ">");

            // check for many
            final boolean many = primitiveManager.isMany(fieldReturnClass);
            // System.out.println("    > isMany: " + many);

            // check for optional
            final boolean optional = schemaKlassField.isAnnotationPresent(nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional.class);
            // System.out.println("    > isOptional: " + optional);

            // check for key
            final boolean key = schemaKlassField.isAnnotationPresent(Key.class);
            // System.out.println("    > isKey: " + key);

            // check for contain
            final boolean contain = schemaKlassField.isAnnotationPresent(Contain.class);
            // System.out.println("    > isContain: " + contain);

            // add its fields, the owner Klass will be added later
            final Field field = factory.Field();
            field.name(fieldName);
            field.contain(contain);
            field.key(key);
            field.many(many);
            field.optional(optional);

            fieldsForKlass.put(fieldName, field);

            // use klassName and fieldName combo here, because the real hashCode can not be calculated yet.
            allFieldsWithReturnType.put(klassName + fieldName, new FieldWithMethod(field, schemaKlassField));
        }
        return fieldsForKlass;
    }

    /**
     * For all the fields created, set the real types of the field.
     * The trick here is that in case of multi value (many), the type is not the collection but
     * but the Generic Return Type which we can take via reflection.
     *
     * @param factory the schema factory
     * @param schema the schema
     * @param allFieldsWithReturnType all the created fields
     */
    private static void wireFieldTypes(
        SchemaFactory factory,
        Schema schema,
        Map<String, FieldWithMethod> allFieldsWithReturnType)
    {
        for (String klassNameFieldNameCombo : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(klassNameFieldNameCombo).method;
            final Field field = allFieldsWithReturnType.get(klassNameFieldNameCombo).field;

            // In case the field is multi value (many), that means that the real type is
            // not given in the method.getReturnType() because this will give Set ot List,
            // BUT the real type is in method.getGenericReturnType().
            final Class<?> fieldTypeClass;

            // in case it is multi field, get the return the Generic Return Type
            if (field.many()) {
                // The type in this case will be Set or List,
                // but the Generic Return Type will be the actual type.
                final ParameterizedType fieldManyType = (ParameterizedType) method.getGenericReturnType();
                fieldTypeClass = (Class<?>) fieldManyType.getActualTypeArguments()[0];
            } else {
                fieldTypeClass = method.getReturnType();
            }

            final Type fieldType = getFieldType(fieldTypeClass, schema, factory);
            field.type(fieldType);

            // System.out.println(" > Wire type: " + field.name() + ".type(" + fieldType.name() + ")");
        }
    }

    /**
     * Wire the inverse fields.
     * @param allFieldsWithReturnType all the created fields
     */
    private static void wireFieldInverse(Map<String, FieldWithMethod> allFieldsWithReturnType) {
        for (String classNameFieldNameCombo : allFieldsWithReturnType.keySet()) {
            final Method method = allFieldsWithReturnType.get(classNameFieldNameCombo).method;
            final Field field = allFieldsWithReturnType.get(classNameFieldNameCombo).field;

            final Field fieldInverseField = buildInverse(method, allFieldsWithReturnType);
            if (fieldInverseField != null) {
                // System.out.println(" > Wire inverse:  " +
                //        field.owner().name() + "." + field.name() +
                //        ".inverse(" + fieldInverseField.owner().name() + "." + fieldInverseField.name() + ")");

                field.inverse(fieldInverseField);
            }
        }
    }

    /**
     * Wire all the super klasses
     * @param types all the types
     * @param cache a type cache
     */
    private static void wireKlassSupers(Map<Type, TypeWithClass> types, Map<String, Type> cache) {
        types.keySet().stream()
            .filter(type -> type.schemaKlass().name().equals("Klass"))
            .map(Klass.class::cast)
            .forEach(klass -> {
                final TypeWithClass typeWithClass = types.get(klass);
                final Set<Klass> superKlasses = buildSupers(typeWithClass.clazz, cache);
                if (superKlasses.size() > 0 && superKlasses.toArray()[0] != null) {
                    klass.supers(superKlasses.toArray(new Klass[superKlasses.size()]));
                }
            });
    }

    /**
     * Wire all the sub klasses
     * @param types all the types
     * @param cache a type cache
     */
    private static void wireKlassSubs(Map<Type, TypeWithClass> types, Map<String, Type> cache) {
        types.keySet().stream()
            .filter(type -> type.schemaKlass().name().equals("Klass"))
            .map(Klass.class::cast)
            .forEach(klass -> {
                final TypeWithClass typeWithClass = types.get(klass);
                final Set<Klass> subKlasses = buildSubs(typeWithClass.clazz, cache);
                if (subKlasses.size() > 0) {
                    final Klass[] subsToArray = subKlasses.toArray(new Klass[subKlasses.size()]);
                    klass.subKlasses(subsToArray);
                }
            });
    }

    /**
     * Wire all the classOf
     * @param types all the types
     * @param schemaKlassesDefinition the class Of the Klass
     */
    public static void wireKlassClassOf(Map<Type, TypeWithClass> types, List<Class<?>> schemaKlassesDefinition) {
        for (Type type : types.keySet()) {
            for (Class klassInterface : schemaKlassesDefinition) {
                if (type.schemaKlass().name().equals("Klass")) {
                    if (klassInterface.getSimpleName().equals(type.name())) {
                        ((Klass) type).classOf(klassInterface);
                        // System.out.println("> Wire " + type.name() + ".classOf(" + klassInterface.getName() + ")");
                    }
                }
            }
        }
    }

    /**
     * Builds an inverse field from a given method.
     * @param method the method
     * @param allFieldsWithReturnType all the created fields
     * @return the inverse field
     */
    private static Field buildInverse(Method method, Map<String, FieldWithMethod> allFieldsWithReturnType) {
        Field fieldInverseField = null;

        // check if there is Inverse annotation in place
        if (method.isAnnotationPresent(Inverse.class)) {
            final Inverse fieldInverse = method.getAnnotation(Inverse.class);

            // get the inverse-other klass
            final Class<?> inverseOther = fieldInverse.other();
            final String inverseOtherName = inverseOther.getSimpleName();

            // get the inverse-field
            final String fieldInverseFieldName = fieldInverse.field();

            // check field name and owner
            for (String classNameFieldNameCombo : allFieldsWithReturnType.keySet()) {
                final Field fieldForSearch = allFieldsWithReturnType.get(classNameFieldNameCombo).field;

                final String fieldForSearchName = fieldForSearch.name();
                final String fieldForSearchOwnerName = fieldForSearch.owner().name();

                if  (fieldForSearchName.equals(fieldInverseFieldName) &&
                     fieldForSearchOwnerName.equals(inverseOtherName))
                {
                    fieldInverseField = fieldForSearch;
                    break;
                }
            }
        }
        return fieldInverseField;
    }

    /**
     * Build type for a field, from a type factory
     * @param fieldReturnClass the type of the field
     * @param schema the schema
     * @param factory the schema factory
     * @return type for a field
     */
    private static Type getFieldType(Class<?> fieldReturnClass, Schema schema, SchemaFactory factory) {
        try {
            return TypeFactory.getTypeFromClass(fieldReturnClass, schema, factory, typesCache);
        } catch (UnknownTypeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Builds the superKlasses of a klass
     * @param schemaKlassDefinition the class of the Klass
     * @param cache a type cache
     * @return a set of super klasses
     */
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

    /**
     * Builds the subKlasses of a klass
     * @param schemaKlassDefinition the class of the Klass
     * @param cache a type cache
     * @return a set of sub klasses
     */
    private static Set<Klass> buildSubs(Class<?> schemaKlassDefinition, Map<String, Type> cache) {
        final Set<Klass> subs = new LinkedHashSet<>();

        for (Type sub : cache.values()) {
            if (sub.schemaKlass().name().equals("Klass")) {
                final Klass subKlass = (Klass) sub;

                for (Klass superKlass : subKlass.supers()) {
                    if (superKlass != null &&
                        superKlass.name() != null &&
                        superKlass.name().equals(schemaKlassDefinition.getSimpleName()))
                    {
                        subs.add(subKlass);
                    }
                }
            }
        }

        return subs.isEmpty() ? Collections.emptySet() : subs;
    }
}