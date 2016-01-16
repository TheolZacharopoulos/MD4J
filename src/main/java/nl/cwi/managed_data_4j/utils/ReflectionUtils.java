package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

import java.lang.reflect.Method;
import java.util.Comparator;

public class ReflectionUtils {
    public static Comparator<? super Method> getMethodsSortingComparator() {

        return (method1, method2) -> {
            Order or1 = method1.getAnnotation(Order.class);
            Order or2 = method2.getAnnotation(Order.class);

            // nulls last
            if (or1 != null && or2 != null) {
                return or1.value() - or2.value();
            } else if (or1 != null && or2 == null) {
                return -1;
            } else if (or1 == null && or2 != null) {
                return 1;
            }
            return method1.getName().compareTo(method2.getName());
        };
    }
}
