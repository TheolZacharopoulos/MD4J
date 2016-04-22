package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.primitives.*;

import java.util.*;

/**
 * Utilities needed for the Java primitives
 * @author Theoogos Zacharopoulos
 */
public class PrimitiveUtils {

    private static final List<AbstractPrimitive> primitives = new LinkedList<>(Arrays.asList(
            new StringPrimitive(),
            new IntegerPrimitive(),
            new FloatPrimitive(),
            new DoublePrimitive(),
            new BooleanPrimitive(),
            new ClassPrimitive()
    ));

    /**
     * Checks if a given class is instance of one of the supported Array classes
     * @param clazz the class to be checked
     * @return true if it is an array class that is supported, false otherwise
     */
    public static boolean isMany(Class<?> clazz) {
        return  (clazz.isArray()) ||
                (Set.class.isAssignableFrom(clazz)) ||
                (List.class.isAssignableFrom(clazz));
    }

    public static boolean isPrimitiveValue(String typeName, Object value) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getSimpleName().equals(typeName) &&
                value.getClass().isAssignableFrom(primitive.getTypeClass()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given class is instance of one of the supported primitives classes
     * @param typeClass the class to check
     * @return true if it is a primitive class that is supported, false otherwise
     */
    public static boolean isPrimitiveClass(Class<?> typeClass) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return true;
            }
        }
        return false;
    }

    public static Object getDefaultValueForPrimitive(Class<?> typeClass) throws UnknownTypeException {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return primitive.getDefaultValue();
            }
        }

        throw new UnknownTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }

    public static Comparator<Object> orderBasedOnClass(Class<?> typeClass) {
        for (AbstractPrimitive primitive : primitives) {
            if (primitive.getTypeClass().isAssignableFrom(typeClass)) {
                return primitive.getComparator();
            }
        }

        return null;
    }
}
