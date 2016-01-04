package nl.cwi.managed_data_4j.schema.bootstrap;

import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

public class BootField implements Field {

    private Klass owner;

    public BootField(Klass owner) {
        this.owner = owner;
    }

    @Override
    public String name(String... name) {
        return Field.class.getSimpleName();
    }

    @Override
    public Type type(Type... type) {
        return null;
    }

    @Override
    public Boolean many(Boolean... many) {
        return true;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return false;
    }

    @Override
    public Field inverse(Field... field) {
        return this;
    }

    @Override
    public Klass owner(Klass... owner) {
        return this.owner;
    }
}
