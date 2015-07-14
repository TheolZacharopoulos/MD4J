package com.cwi.managed_data.klass_system;

import java.util.Set;

public interface Klass extends Type {
    Set<Field> fields(Field ...field);
    Set<Klass> supers(Klass ...sup);

    @Inverse(other=Klass.class, field="supers")
    Set<Klass> subs(Klass ...sub);

    /**
     * The Schema of a Klass is the Schema it belongs to.
     */
    @Inverse(other=Schema.class, field="classes")
    Schema schema(Schema ...schema);
}