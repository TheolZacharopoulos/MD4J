package nl.cwi.managed_data_4j.data_managers;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;

import java.lang.reflect.InvocationHandler;

/**
 * The Data manager
 */
public interface IFactory extends InvocationHandler {

    /**
     * This method and should be implemented from the derived data_managers
     * in order to create specific Managed Objects.
     *
     * @param klass the Managed Object schema Klass
     * @param _inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    MObject createManagedObject(Klass klass, Object... _inits);
}
