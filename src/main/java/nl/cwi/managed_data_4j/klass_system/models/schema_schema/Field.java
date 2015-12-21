package nl.cwi.managed_data_4j.klass_system.models.schema_schema;

public interface Field {

    /**
     * A name fields that is used as a unique key
     */
    String name(String... name);

    Type type(Type... type);

    Boolean many(Boolean... many);

    Boolean optional(Boolean... optional);

    /**
     * The inverse of a Field is a Field that it is inverse of.
     */
    @Inverse(other=Field.class, field="inverse")
    Field inverse(Field... field);

    /**
     * The owner of a Field is the Klass it belongs to.
     */
    @Inverse(other=Klass.class, field="fields")
    Klass owner(Klass... owner);
}