package com.cwi.managed_data.klass_system;

import java.util.HashSet;
import java.util.Set;

public interface Schema {
    Set<Type> types(Type ...type);

    default Set<Klass> classes() {
        return new HashSet<>();
    }
}