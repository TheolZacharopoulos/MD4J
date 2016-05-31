package nl.cwi.managed_data_4j.language.schema.models.implementation;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.schema.models.definition.Type;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

/**
 * Represents an implementation of a Schema Schema definition.
 * @author Theologos Zacharopoulos
 */
public class SchemaImpl implements Schema {
    protected Set<Type> types;
    protected Klass schemaKlass;

    public SchemaImpl() {
        this(null, null);
    }
    
    public SchemaImpl(Set<Type> types, Klass schemaKlass) {
        this.types = types;
        this.schemaKlass = schemaKlass;
    }

    @Contain
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
    public Klass schemaKlass(Klass... schemaKlass) {
        if (schemaKlass.length > 0) {
            this.schemaKlass = schemaKlass[0];
        }
        return this.schemaKlass;
    }
}
