package nl.cwi.managed_data_4j.language.data_manager;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

/**
 * The Data manager interface
 * @author Theologos Zacharopoulos
 */
public interface IDataManager {
    <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces);
    M createManagedObject(Klass klass, Object... inits);
}
