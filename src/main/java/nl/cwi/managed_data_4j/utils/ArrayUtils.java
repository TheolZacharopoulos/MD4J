package nl.cwi.managed_data_4j.utils;

import java.util.List;
import java.util.Set;

public class ArrayUtils {

    public static boolean isArray(Class<?> clazz) {
        if (clazz.isArray()) return true;
        if (clazz.isAssignableFrom(Set.class)) return true;
        if (clazz.isAssignableFrom(List.class)) return true;

        return false;
    }
}
