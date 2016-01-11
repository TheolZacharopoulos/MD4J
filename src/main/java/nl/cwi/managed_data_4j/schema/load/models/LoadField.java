package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.implementation.AbstractField;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public class LoadField extends AbstractField {

    private String name;
    private Type type;
    private Boolean many;
    private Boolean optional;
    private Field inverse;

    public LoadField(Schema schema, Klass owner) {
        super(schema, owner);
    }

    public LoadField(
        String name,
        Schema schema,
        Klass owner,
        Type type,
        Boolean many,
        Boolean optional,
        Field inverse)
    {
        super(schema, owner);

        this.setName(name);
        this.setType(type);
        this.setMany(many);
        this.setOptional(optional);
        this.setInverse(inverse);
    }

    @Override
    public String name(String... name) {
        return this.getName();
    }

    @Override
    public Type type(Type... type) {
        return this.getType();
    }

    @Override
    public Boolean many(Boolean... many) {
        return this.getMany();
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return this.getOptional();
    }

    @Override
    public Field inverse(Field... field) {
        return this.getInverse();
    }

    public Schema getSchema() {
        return schema;
    }

    public Klass getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Boolean getMany() {
        return many;
    }

    public Boolean getOptional() {
        return optional;
    }

    public Field getInverse() {
        return inverse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setMany(Boolean many) {
        this.many = many;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public void setInverse(Field inverse) {
        this.inverse = inverse;
    }
}
