package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Utilities needed for the Java primitives
 * @author Theoogos Zacharopoulos
 */
public class PrimitiveUtils {

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
        boolean ok = false;

        switch (typeName) {
            case "String":
                if (value instanceof String) ok = true;
                break;

            case "Integer":
            case "int":
                if (value instanceof Integer) ok = true;
                break;

            case "Boolean":
            case "boolean":
                if (value instanceof Boolean) ok = true;
                break;

            case "Float":
                if (value instanceof Float) ok = true;
                break;

            case "Double":
                if (value instanceof Double) ok = true;
                break;

            case "Class":
                if (value instanceof Class) ok = true;
                break;

            case "Object":
                if (value instanceof String  ||
                    value instanceof Integer ||
                    value instanceof Boolean ||
                    value instanceof Float   ||
                    value instanceof Double)
                {
                    ok = true;
                }
                break;
        }

       return ok;
    }

    /**
     * Checks if a given class is instance of one of the supported primitives classes
     * @param typeClass the class to check
     * @return true if it is a primitive class that is supported, false otherwise
     */
    public static boolean isPrimitiveClass(Class<?> typeClass) {
        return  (String.class.isAssignableFrom(typeClass))  ||
                (Integer.class.isAssignableFrom(typeClass)) ||
                (int.class.isAssignableFrom(typeClass))     ||
                (Boolean.class.isAssignableFrom(typeClass)) ||
                (boolean.class.isAssignableFrom(typeClass)) ||
                (Float.class.isAssignableFrom(typeClass))   ||
                (Double.class.isAssignableFrom(typeClass))  ||
                (Class.class.isAssignableFrom(typeClass));
    }

    public static Object getDefaultValueForPrimitive(Class<?> typeClass) throws UnknownTypeException {
        if (String.class.isAssignableFrom(typeClass)) return "";
        if (Integer.class.isAssignableFrom(typeClass)) return 0;
        if (int.class.isAssignableFrom(typeClass)) return 0;
        if (Boolean.class.isAssignableFrom(typeClass)) return false;
        if (boolean.class.isAssignableFrom(typeClass)) return false;
        if (Float.class.isAssignableFrom(typeClass)) return 0.0;
        if (Double.class.isAssignableFrom(typeClass)) return 0.0;
        if (Class.class.isAssignableFrom(typeClass)) return null;

        throw new UnknownTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }

    public static Comparator<Object> orderBasedOnClass(Class<?> typeClass) {
        if (String.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((String) o1).compareTo((String) o2);
        }

        if (Integer.class.isAssignableFrom(typeClass) || int.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((Integer) o1).compareTo((Integer) o2);
        }

        if (Boolean.class.isAssignableFrom(typeClass) || boolean.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((Boolean) o1).compareTo((Boolean) o2);
        }

        if (Float.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((Float) o1).compareTo((Float) o2);
        }

        if (Double.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((Double) o1).compareTo((Double) o2);
        }

        if (Class.class.isAssignableFrom(typeClass)) {
            return (o1, o2) -> ((Class) o1).getSimpleName().compareTo(((Class) o2).getSimpleName());
        }

        return null;
    }
}
