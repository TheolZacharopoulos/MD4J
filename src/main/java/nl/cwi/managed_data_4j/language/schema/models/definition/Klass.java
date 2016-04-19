package nl.cwi.managed_data_4j.language.schema.models.definition;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional;

import java.util.Set;

/**
 * Klass definition
 * @author Theologos Zacharopoulos
 */
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
    Set<Klass> subKlasses(Klass... subKlasses);


    /**
     * The field which may represent the key for this Type.
     */
    @Optional
    Field key(Field... key);
}