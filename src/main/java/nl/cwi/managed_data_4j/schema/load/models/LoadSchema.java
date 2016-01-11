package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.implementation.AbstractSchema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Set;

public class LoadSchema extends AbstractSchema {

    public LoadSchema(Set<Type> types, Set<Class> klassInterfaces) {
        super(types, klassInterfaces);
    }

    public void setKlassInterfaces(Set<Class> klassInterfaces) {
        this.klassInterfaces = klassInterfaces;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }
}
