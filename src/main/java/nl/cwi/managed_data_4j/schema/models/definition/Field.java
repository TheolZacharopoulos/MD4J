package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Key;

public interface Field {

    /**
     * A name fields that is used as a unique key
     */
    @Key
    String name(String... name);

    Boolean many(Boolean... many);

    Boolean optional(Boolean... optional);

    // TODO:
//    Boolean key(Boolean... key);
//    Boolean content(Boolean... content);

    Type type(Type... type);

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