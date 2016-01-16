package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

import java.util.Set;

public interface Klass extends Type {

    @Contain
    @Order(value=3)
    Set<Field> fields(Field... field);

    /**
     * The superclasses, in order to keep type hierarchy
     */
    @Order(value=4)
    Set<Klass> supers(Klass... supers);

    /**
     * The subclasses, in order to keep type hierarchy
     */
    @Order(value=5)
    @Inverse(other=Klass.class, field="supers")
    Set<Klass> subklasses(Klass... subklasses);

    /**
     * The Schema of a Klass is the Schema it belongs to.
     */
    @Order(value=6)
    @Inverse(other=Schema.class, field="klasses")
    Schema schema(Schema... schema);

    @Order(value=7)
    Class<?> classOf(Class<?> ...classOf);
}