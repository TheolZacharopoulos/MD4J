package nl.cwi.managed_data_4j.language.data_manager;

/**
 * The Data manager interface
 * @author Theologos Zacharopoulos
 */
public interface IFactory {
    <T> T make();
}
