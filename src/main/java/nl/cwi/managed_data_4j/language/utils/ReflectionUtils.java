package nl.cwi.managed_data_4j.language.utils;

import java.lang.reflect.Method;
import java.util.Comparator;

public class ReflectionUtils {
    public static Comparator<Method> methodsOrderComparator() {

        /**
         * Alphabetical order comparator for methods,
         * takes only the primitives and non-many types.
         */
        return (Method o1, Method o2) -> {

            // Order only the primitives and non-many,
            // the rest put them at the end.
            boolean isM1Comparable = (
                (!PrimitiveUtils.isMany(o1.getReturnType())) &&
                PrimitiveUtils.isPrimitive(o1.getReturnType().getSimpleName())
            );

            boolean isM2Comparable = (
                (!PrimitiveUtils.isMany(o2.getReturnType())) &&
                PrimitiveUtils.isPrimitive(o2.getReturnType().getSimpleName())
            );

            if ((!isM1Comparable) && (!isM2Comparable)) {
                return 0;
            }

            if (!isM1Comparable) {
                return 1;
            }

            if (!isM2Comparable) {
                return -1;
            }

            return o1.getName().compareTo(o2.getName());
        };
    }
}
