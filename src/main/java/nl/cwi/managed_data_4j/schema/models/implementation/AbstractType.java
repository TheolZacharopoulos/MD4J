package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public abstract class AbstractType implements Type {

    protected Schema schema;

    public AbstractType(Schema schema) {
        this.schema = schema;
    }

    @Override
    public abstract String name(String... name);

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
        if (o == null || getClass() != o.getClass()) return false;

        AbstractType that = (AbstractType) o;

        if (!this.name().equals(that.name())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        //TODO: Add schema
        return this.name() != null ? this.name().hashCode() : 0;
    }
}
