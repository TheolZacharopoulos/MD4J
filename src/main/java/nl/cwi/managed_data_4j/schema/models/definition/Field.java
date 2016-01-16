package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

public interface Field {

    /**
     * A name fields that is used as a unique key
     */
    @Key
    @Order(value=1)
    String name(String... name);

    @Order(value=2)
    Boolean many(Boolean... many);

    @Order(value=3)
    Boolean optional(Boolean... optional);

    @Order(value=4)
    Boolean key(Boolean... key);

    @Order(value=5)
    Boolean contain(Boolean... contain);

    @Order(value=6)
    Type type(Type... type);

    /**
     * The inverse of a Field is a Field that it is inverse of.
     */
    @Order(value=7)
    @Inverse(other=Field.class, field="inverse")
    Field inverse(Field... field);

    /**
     * The owner of a Field is the Klass it belongs to.
     */
    @Order(value=8)
    @Inverse(other=Klass.class, field="fields")
    Klass owner(Klass... owner);
}