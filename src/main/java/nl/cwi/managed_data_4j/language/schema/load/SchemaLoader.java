package nl.cwi.managed_data_4j.language.schema.load;

import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.language.schema.models.implementation.FieldImpl;
import nl.cwi.managed_data_4j.language.schema.models.implementation.KlassImpl;
import nl.cwi.managed_data_4j.language.schema.models.implementation.PrimitiveImpl;
import nl.cwi.managed_data_4j.language.utils.PrimitiveUtils;
import nl.cwi.managed_data_4j.language.utils.ReflectionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Dynamically loads schemas.
 * This is where the whole loading process is done,
 * given Schema definition (interface), this class provides an instance
 * of this schema.
 *
 * @author Theologos Zacharopoulos
 */
public class SchemaLoader {

    private static final org.apache.log4j.Logger logger = LogManager.getLogger(SchemaLoader.class.getName());

    /**
     * Helper private class to keep Fields with their Method which define them.
     */
    private static class FieldWithMethod {

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
    private static class TypeWithClass {

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
     * This is a special case since every Managed data has a schemaKlass,
     * which means that during the loading of managed objects (e.g. Point)
     * we need an already created Klass that would used in the schemaKlass field definition.
     * However, if the Klass schema definition has not been given as input in schemaKlassesDef,
     * then the schema loader will never created, and we will have an error.
     * Thus, we check if the Klass definition is given in schemaKlassesDef, and if not, then
     * we can the Klass definition from the schemaSchema itself.
     *
     * @param schemaSchema the schemaSchema
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     */
    private static void setupCacheForSchemaKlass(Schema schemaSchema, Class<?>... schemaKlassesDef) {
        boolean includesSchemaDef = Arrays.stream(schemaKlassesDef).anyMatch(aClass -> aClass.getSimpleName().equals("Schema"));
        boolean includesTypeDef = Arrays.stream(schemaKlassesDef).anyMatch(aClass -> aClass.getSimpleName().equals("Type"));
        boolean includesPrimitiveDef = Arrays.stream(schemaKlassesDef).anyMatch(aClass -> aClass.getSimpleName().equals("Primitive"));
        boolean includesKlassDef = Arrays.stream(schemaKlassesDef).anyMatch(aClass -> aClass.getSimpleName().equals("Klass"));
        boolean includesFieldDef = Arrays.stream(schemaKlassesDef).anyMatch(aClass -> aClass.getSimpleName().equals("Field"));

        if (!includesSchemaDef) {
            typesCache.put("Schema", schemaSchema.types().stream().filter(type -> type.name().equals("Schema")).findFirst().get());
        }

        if (!includesTypeDef) {
            typesCache.put("Type", schemaSchema.types().stream().filter(type -> type.name().equals("Type")).findFirst().get());
        }

        if (!includesPrimitiveDef) {
            typesCache.put("Primitive", schemaSchema.types().stream().filter(type -> type.name().equals("Primitive")).findFirst().get());
        }

        if (!includesKlassDef) {
            typesCache.put("Klass", schemaSchema.types().stream().filter(type -> type.name().equals("Klass")).findFirst().get());
        }

        if (!includesFieldDef) {
            typesCache.put("Field", schemaSchema.types().stream().filter(type -> type.name().equals("Field")).findFirst().get());
        }
    }

    /**
     * Convert from a schema definitions (interface) to an instance of Schema.
     * @param factory the factory which creates the schema
     * @param schemaSchema the schemaSchema
     * @param schemaKlassesDef the schemas definitions (interfaces) to be converted.
     * @return the instance of Schema
     */
    public static Schema load(SchemaFactory factory, Schema schemaSchema, Class<?>... schemaKlassesDef) {

        logger.setLevel(Level.OFF);
        logger.debug("SchemaFactory: create schema");

        setupCacheForSchemaKlass(schemaSchema, schemaKlassesDef);

        // create an empty schema using the factory, will wire it later
        final Schema schema = factory.Schema();

        // build the types from the schema klasses definition
        final Set<Type> types = buildTypesFromClasses(factory, schema, schemaKlassesDef);

        // wire the types on schema
        schema.types(types.toArray(new Type[types.size()]));

        // get the schema's schemaKlass
        final Klass schemaSchemaKlass = schemaSchema.klasses().stream()
            .filter(klass -> klass.name().equals("Schema"))
            .findFirst().get();

        // wire the schema's schemaKlass
        schema.schemaKlass(schemaSchemaKlass);

        // get the primitive's schemaKlass
        final Klass primitiveSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Primitive"))
                .findFirst().get();

        // wire the primitive's schemaKlass
        for (Primitive primitive : schemaSchema.primitives()) {
            primitive.schemaKlass(primitiveSchemaKlass);
        }

        // get the klass's schemaKlass
        final Klass klassSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Klass"))
                .findFirst().get();

        // wire the klasses's schemaKlass
        for (Klass klass : schemaSchema.klasses()) {
            klass.schemaKlass(klassSchemaKlass);
        }

        // get the field's schemaKlass
        final Klass fieldSchemaKlass = schemaSchema.klasses().stream()
                .filter(klass -> klass.name().equals("Field"))
                .findFirst().get();

        // wire the field's schemaKlass
        for (Klass klass : schemaSchema.klasses()) {
            for (Field field : klass.fields()) {
                field.schemaKlass(fieldSchemaKlass);
            }
        }

        return schema;
    }

    private static Set<Type> buildTypesFromClasses(
            SchemaFactory factory,
            Schema schema,
            Class<?>... schemaKlassesDefinition)
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

            logger.debug("> SchemaFactory: create klass " + klassName);
            final Map<String, Field> fieldsForKlass =
                buildFieldsFromMethods(klassName, factory, schemaKlassDefinition, allFieldsWithReturnType);

            // create a new klass
            final Klass klass = factory.Klass();
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
        wireFieldTypeKeys(allFieldsWithReturnType);

        wireKlassSupers(types, typesCache);
        wireKlassSubs(types, typesCache);
        wireKlassClassOf(types, schemaKlassesDefinition);

        typesCache.clear();
        return types.keySet();
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
            .filter(Klass.class::isInstance)
            .map(Klass.class::cast)
            .forEach(klass -> {
                final TypeWithClass typeWithClass = types.get(klass);
                final Set<Klass> superKlasses = buildSupers(typeWithClass.clazz, cache);
                if (superKlasses.size() > 0) {
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
            .filter(Klass.class::isInstance)
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

    /**
     * From all the created fields that are type of Klass,
     * get their klasses and set as key the first field found as KEY.
     *
     * We dont need that for primitives since it is done during the type assignment.
     * @param allFieldsWithReturnType all the created fields
     */
    private static void wireFieldTypeKeys(Map<String, FieldWithMethod> allFieldsWithReturnType) {

        for (String classNameFieldNameCombo : allFieldsWithReturnType.keySet()) {
            final Field field = allFieldsWithReturnType.get(classNameFieldNameCombo).field;
            final Type fieldType = field.type();

            // we don't care about keys if it is not many
            if (!field.many()) continue;

            // in case it is a primitive, then we need to check if it needs to have a key
            // that happens when the primitive is a type of Set (needs a key).
            // in that case we set the key to the field itself.
            if (field.type().schemaKlass().name().equals("Primitive")) {

                final Method method = allFieldsWithReturnType.get(classNameFieldNameCombo).method;
                if (PrimitiveUtils.doesManyNeedsAKey(method.getReturnType())) {
                    fieldType.key(field);
                }

            } else {

                // in case it is a Klass then just set as key the first field found as KEY,
                // null if none found.
                final Klass klassFieldType = (Klass) fieldType;
                final Field key = klassFieldType.fields().stream()
                        .filter(Field::key)
                        .findFirst()
                        .orElse(null);
                fieldType.key(key);
            }
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

            logger.debug("  > SchemaFactory: create field " + fieldName + " <" + fieldReturnClass.getSimpleName() + ">");

            // check for many
            final boolean many = PrimitiveUtils.isMany(fieldReturnClass);
            logger.debug("    > isMany: " + many);

            // check for optional
            final boolean optional = schemaKlassField.isAnnotationPresent(nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional.class);
            logger.debug("    > isOptional: " + optional);

            // check for key
            final boolean key = schemaKlassField.isAnnotationPresent(Key.class);
            logger.debug("    > isKey: " + key);

            // check for contain
            final boolean contain = schemaKlassField.isAnnotationPresent(Contain.class);
            logger.debug("    > isContain: " + contain);

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
            if (sub instanceof Klass) {
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