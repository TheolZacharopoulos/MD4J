package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public class FieldImpl implements Field {

    protected String name = null;
    protected Boolean many = null;
    protected Boolean optional = null;
    protected Type type = null;
    protected Field inverse = null;
    protected Klass owner = null;

    public FieldImpl(String name, Boolean many, Boolean optional) {
        this(name, many, optional, null, null, null);
    }

    public FieldImpl(
        String name,
        Boolean many,
        Boolean optional,
        Type type,
        Field inverse,
        Klass owner)
    {
        this.name = name;
        this.type = type;
        this.many = many;
        this.optional = optional;
        this.inverse = inverse;
        this.owner = owner;
    }

    @Override
    public String name(String... name) {
        if (name.length > 0) {
            this.name = name[0];
        }
        return this.name;
    }

    @Override
    public Type type(Type... type) {
        if (type.length > 0) {
            this.type = type[0];
        }
        return this.type;
    }

    @Override
    public Boolean many(Boolean... many) {
        if (many.length > 0) {
            this.many = many[0];
        }
        return this.many;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        if (optional.length > 0) {
            this.optional = optional[0];
        }
        return this.optional;
    }

    @Override
    public Field inverse(Field... field) {
        if (field.length > 0) {
            this.inverse = field[0];
        }
        return this.inverse;
    }

    @Override
    public Klass owner(Klass... owner) {
        if (owner.length > 0) {
            this.owner = owner[0];
        }
        return this.owner;
    }
}
