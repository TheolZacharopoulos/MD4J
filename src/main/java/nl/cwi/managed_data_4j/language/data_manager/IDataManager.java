package nl.cwi.managed_data_4j.language.data_manager;

import nl.cwi.managed_data_4j.language.IFactory;

/**
 * The Data manager interface
 * @author Theologos Zacharopoulos
 */
public interface IDataManager {
    <T extends IFactory> T make();
}
