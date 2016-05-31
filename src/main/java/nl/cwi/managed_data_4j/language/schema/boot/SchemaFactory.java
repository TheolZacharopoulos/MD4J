package nl.cwi.managed_data_4j.language.schema.boot;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

/**
 * A factory for schema klasses
 * @author Theologos Zacharopoulos
 */
public interface SchemaFactory extends IFactory {

    /**
     * Builds a new empty Schema
     */
    Schema Schema();

    /**
     * Builds a new Primitive with its type name
     */
    Primitive Primitive();

    /**
     * Builds a new empty Klass
     */
    Klass Klass();

    /**
     * Builds a new empty Field
     */
    Field Field();
}
