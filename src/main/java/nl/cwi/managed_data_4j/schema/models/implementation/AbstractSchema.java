package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Set;

public class AbstractSchema implements Schema {
    protected Set<Type> types;
    protected Set<Class> klassInterfaces;

    public AbstractSchema(Set<Type> types, Set<Class> klassInterfaces) {
        this.types = types;
        this.klassInterfaces = klassInterfaces;
    }

    @Override
    public Set<Type> types(Type... type) {
        return this.types;
    }

    @Override
    public Set<Class> klassInterfaces(Class... interfaces) {
        return this.klassInterfaces;
    }
}
