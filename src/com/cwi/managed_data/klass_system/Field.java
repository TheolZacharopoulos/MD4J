package com.cwi.managed_data.klass_system;

public interface Field {
    String name(String ...name);
    Type type(Type ...type);
    Boolean many(Boolean ...many);
    Boolean optional(Boolean ...optional);
    Field inverse(Field ...field);

    @Inverse(other=Klass.class, field="fields")
    Klass owner(Klass ...owner);
}