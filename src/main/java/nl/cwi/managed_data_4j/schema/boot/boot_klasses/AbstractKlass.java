package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractKlass implements Klass {

    protected Schema schema;

    public AbstractKlass(Schema schema) {
        this.schema = schema;
    }

    @Override
    public abstract Set<Field> fields(Field... field);

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.emptySet();
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return Collections.emptySet();
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractKlass that = (AbstractKlass) o;

        if (!this.name().equals(that.name())) return false;
        if (!this.supers().equals(that.supers())) return false;
        if (!this.subklasses().equals(that.subklasses())) return false;
        if (!this.fields().equals(that.fields())) return false;

        return true;
    }
}
