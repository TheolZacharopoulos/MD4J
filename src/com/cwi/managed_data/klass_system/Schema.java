package com.cwi.managed_data.klass_system;

import java.util.Set;

public interface Schema {
    Set<Type> types(Type ...type);

// TODO: ???
//    default Set<Klass> classes() {
//        return null;
//    }
}