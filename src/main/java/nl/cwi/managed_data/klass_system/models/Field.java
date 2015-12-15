package nl.cwi.managed_data.klass_system.models;

public interface Field {
    String name(String ...name);
    Type type(Type ...type);
    Boolean many(Boolean ...many);
    Boolean optional(Boolean ...optional);

    /**
     * The inverse of a Field is a Field that it is inverse of.
     */
    Field inverse(Field ...field);

    /**
     * The owner of a Field is the Klass it belongs to.
     */
    @Inverse(other=Klass.class, field="fields")
    Klass owner(Klass ...owner);
}