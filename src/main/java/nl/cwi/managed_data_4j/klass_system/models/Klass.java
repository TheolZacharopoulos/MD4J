package nl.cwi.managed_data_4j.klass_system.models;

import java.util.Set;

public interface Klass extends Type {

    Set<Field> fields(Field... field);

    /**
     * The superclasses, in order to keep type hierarchy
     */
    Set<Klass> supers(Klass... sup);

    /**
     * The superclasses, in order to keep type hierarchy
     */
    @Inverse(other=Klass.class, field="supers")
    Set<Klass> subs(Klass... sub);

    /**
     * The Schema of a Klass is the Schema it belongs to.
     */
    @Inverse(other=Schema.class, field="klasses")
    Schema schema(Schema... schema);

    /**
     * The Interface of which this Klass is made from.
     */
    Class klassInterface(Class... interfaces);
}