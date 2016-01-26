package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Optional;

import java.util.Set;

public interface Klass extends Type {

    @Contain
    Set<Field> fields(Field... field);

    /**
     * The superclasses, in order to keep type hierarchy
     */
    Set<Klass> supers(Klass... supers);

    /**
     * The subclasses, in order to keep type hierarchy
     */
    @Inverse(other=Klass.class, field="supers")
    Set<Klass> subklasses(Klass... subklasses);

    /**
     * The Schema of a Klass is the Schema it belongs to.
     */
    @Inverse(other=Schema.class, field="klasses")
    Schema schema(Schema... schema);

    /**
     * Which Java class does type Klass is of.
     */
    Class<?> classOf(Class<?> ...classOf);

    /**
     * The field which may represent the key for this Klass.
     */
    @Optional
    Field key(Field... key);
}