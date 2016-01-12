package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class SchemaImpl implements Schema {
    protected Set<Type> types;
    protected Set<Class> klassInterfaces;

    public SchemaImpl() {
        this(null, null);
    }
    
    public SchemaImpl(Set<Type> types, Set<Class> klassInterfaces) {
        this.types = types;
        this.klassInterfaces = klassInterfaces;
    }

    @Override
    public Set<Type> types(Type... type) {
        if (type.length > 0) {
            if (type.length > 1) {
                this.types = new LinkedHashSet<>();
                for (Type aType : type) {
                    this.types.add(aType);
                }
            } else {
                this.types = Collections.singleton(type[0]);
            }
        }
        return this.types;
    }

    @Override
    public Set<Class> klassInterfaces(Class... interfaces) {
        if (interfaces.length > 0) {
            if (interfaces.length > 1) {
                this.klassInterfaces = new LinkedHashSet<>();
                for (Class<?> aClass : interfaces) {
                    this.klassInterfaces.add(aClass);
                }
            } else {
                this.klassInterfaces = Collections.singleton(interfaces[0]);
            }
        }
        return this.klassInterfaces;
    }
}
