package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

import java.util.Set;

public class LoadSchema implements Schema {

    private Set<Class> klassInterfaces;
    private Set<Type> types;

    public LoadSchema(Set<Class> klassInterfaces, Set<Type> types) {
        this.klassInterfaces = klassInterfaces;
        this.types = types;
    }

    @Override
    public Set<Class> klassInterfaces(Class... interfaces) {
        return this.klassInterfaces;
    }

    @Override
    public Set<Type> types(Type... type) {
        return this.types;
    }

    public void setKlassInterfaces(Set<Class> klassInterfaces) {
        this.klassInterfaces = klassInterfaces;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }
}
