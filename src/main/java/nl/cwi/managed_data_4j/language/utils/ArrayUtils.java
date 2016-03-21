package nl.cwi.managed_data_4j.language.utils;

import java.util.List;
import java.util.Set;

public class ArrayUtils {

    public static boolean isMany(Class<?> clazz) {
        if (clazz.isArray()) return true;
        if (Set.class.isAssignableFrom(clazz)) return true;
        if (List.class.isAssignableFrom(clazz)) return true;

        return false;
    }
}
