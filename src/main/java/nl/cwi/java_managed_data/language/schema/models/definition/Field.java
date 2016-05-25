package nl.cwi.java_managed_data.language.schema.models.definition;

import nl.cwi.java_managed_data.M;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Inverse;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Key;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Optional;

/**
 * Field definition
 * @author Theologos Zacharopoulos
 */
public interface Field extends M {

    /**
     * A name fields that is used as a unique key
     */
    @Key
    String name(String... name);

    Boolean many(Boolean... many);

    Boolean optional(Boolean... optional);

    Boolean key(Boolean... key);

    Boolean contain(Boolean... contain);

    Type type(Type... type);

    /**
     * The inverse of a Field is a Field that it is inverse of.
     */
    @Optional
    @Inverse(other=Field.class, field="inverse")
    Field inverse(Field... field);

    /**
     * The owner of a Field is the Klass it belongs to.
     */
    @Inverse(other=Klass.class, field="fields")
    Klass owner(Klass... owner);
}