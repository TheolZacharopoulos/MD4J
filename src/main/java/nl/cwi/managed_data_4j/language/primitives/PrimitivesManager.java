package nl.cwi.managed_data_4j.language.primitives;

import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Utilities needed for the Java primitives
 * @author Theoogos Zacharopoulos
 */
public class PrimitivesManager {

    // initial primitives.
    private final Set<AbstractPrimitive> primitives = new LinkedHashSet<>();

    private static PrimitivesManager instance = null;

    public static PrimitivesManager getInstance() {
        if (instance == null) {
            instance = new PrimitivesManager();
        }
        return instance;
    }

    private PrimitivesManager() {}

    public void loadPrimitives(Class<?> primitivesDefinition) {
        System.out.println(" > Primitives Manager: Loading primitives");
        for (Method primDef : primitivesDefinition.getMethods()) {
            Class<?> primitiveClass = primDef.getReturnType();
            if (isMany(primitiveClass)) {
                // The type in this case will be Set or List,
                // but the Generic Return Type will be the actual type.
                final ParameterizedType fieldManyType = (ParameterizedType) primDef.getGenericReturnType();
                primitiveClass = (Class<?>) fieldManyType.getActualTypeArguments()[0];
            }

            String primitiveName = primDef.getName();

            // We use _ for convention in case of reserved words on native types.
            if (primitiveName.startsWith("_")) {
                primitiveName = primitiveName.substring(1);
            }

            final Object defaultValue = getDefaultValue(primitiveClass);
            final Comparator<Object> comparator = getComparator(primitiveClass);

            primitives.add(new AbstractPrimitive(defaultValue, primitiveClass, primitiveName, comparator) {});
            System.out.println("    > Primitives Manager: primitive added " + primitiveName);
        }
    }

    private Object getDefaultValue(Class<?> primitiveClass) {
        if (primitiveClass.isAssignableFrom(String.class))  return "";
        if (primitiveClass.isAssignableFrom(Integer.class)) return 0;
        if (primitiveClass.isAssignableFrom(Float.class))   return 0.f;
        if (primitiveClass.isAssignableFrom(Double.class))  return 0.;
        if (primitiveClass.isAssignableFrom(Boolean.class)) return false;
        return null;
    }

    private Comparator<Object> getComparator(Class<?> primitiveClass) {
        if (primitiveClass.isAssignableFrom(String.class))
            return (o1, o2) -> ((String) o1).compareTo((String) o2);

        if (primitiveClass.isAssignableFrom(Integer.class))
            return (o1, o2) -> ((Integer) o1).compareTo((Integer) o2);

        if (primitiveClass.isAssignableFrom(Float.class))
            return (o1, o2) -> ((Float) o1).compareTo((Float) o2);

        if (primitiveClass.isAssignableFrom(Double.class))
            return (o1, o2) -> ((Double) o1).compareTo((Double) o2);

        if (primitiveClass.isAssignableFrom(Boolean.class))
            return (o1, o2) -> ((Boolean) o1).compareTo((Boolean) o2);

        if (primitiveClass.isAssignableFrom(Class.class))
            return (o1, o2) -> ((Class) o1).getSimpleName().compareTo(((Class) o2).getSimpleName());

        return null;
    }

    /**
     * Checks if a given class is instance of one of the supported Array classes
     * @param clazz the class to be checked
     * @return true if it is an array class that is supported, false otherwise
     */
    public boolean isMany(Class<?> clazz) {
        return  (clazz.isArray()) ||
                (Set.class.isAssignableFrom(clazz)) ||
                (List.class.isAssignableFrom(clazz));
    }

    public boolean isPrimitiveValue(String typeName, Object value) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getSimpleName().equals(typeName) ||
                value.getClass().isAssignableFrom(primitive.getTypeClass()))
            {
                return true;
            }
        }

        if (typeName.equals("Object")) {
            for (AbstractPrimitive primitive : primitives) {
                if (value.getClass().isAssignableFrom(primitive.getTypeClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if a given class is instance of one of the supported primitives classes
     * @param typeClass the class to check
     * @return true if it is a primitive class that is supported, false otherwise
     */
    public boolean isPrimitiveClass(Class<?> typeClass) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return true;
            }
        }
        return false;
    }

    public Object getDefaultValueForPrimitive(Class<?> typeClass) throws UnknownTypeException {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return primitive.getDefaultValue();
            }
        }

        throw new UnknownTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }

    public Comparator<Object> orderBasedOnClass(Class<?> typeClass) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return primitive.getComparator();
            }
        }

        return null;
    }
}
