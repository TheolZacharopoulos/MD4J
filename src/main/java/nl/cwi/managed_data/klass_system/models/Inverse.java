package nl.cwi.managed_data.klass_system.models;

public @interface Inverse {
    Class other();
    String field();
}
