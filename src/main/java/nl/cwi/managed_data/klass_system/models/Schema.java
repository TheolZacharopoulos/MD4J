package nl.cwi.managed_data.klass_system.models;

import java.util.HashSet;
import java.util.Set;

public interface Schema {
    Set<Type> types(Type ...type);

    default Set<Klass> classes() {
        return new HashSet<>();
    }
}