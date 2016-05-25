package nl.cwi.java_managed_data.language.data_manager;

import nl.cwi.java_managed_data.IFactory;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

/**
 * The Data manager interface
 * @author Theologos Zacharopoulos
 */
public interface IDataManager {
    <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces);
}
