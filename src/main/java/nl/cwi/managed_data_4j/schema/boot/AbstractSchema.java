package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

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
