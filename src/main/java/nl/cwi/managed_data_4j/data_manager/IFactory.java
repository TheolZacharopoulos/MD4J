package nl.cwi.managed_data_4j.data_manager;

/**
 * The Data manager interface
 */
public interface IFactory {
    <T> T make();
}
