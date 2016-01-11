package nl.cwi.managed_data_4j.data_manager;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 */
public class BasicFactory extends AbstractFactory {

    @Override
    public MObject createManagedObject(Klass klass, Object... inits) {
        return new MObject(klass, this, inits); // return a basic managed object
    }
}