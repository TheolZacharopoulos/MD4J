package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

public abstract class AbstractPrimitive implements Primitive {

    protected Schema schema;

    public AbstractPrimitive(Schema schema) {
        this.schema = schema;
    }

    @Override
    public abstract String name(String ...name);

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        //TODO: Add schema
        if (this == o) return true;

        AbstractPrimitive that = (AbstractPrimitive) o;

        if (!this.name().equals(that.name())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        //TODO: Add schema
        return this.name() != null ? this.name().hashCode() : 0;
    }
}
