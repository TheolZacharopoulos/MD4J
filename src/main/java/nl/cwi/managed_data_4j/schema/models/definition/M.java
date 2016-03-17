package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;

/**
 * M definition
 * @author Theologos Zacharopoulos
 */
public interface M {
    @Contain
    Klass schemaKlass(Klass... schemaKlass);
}
