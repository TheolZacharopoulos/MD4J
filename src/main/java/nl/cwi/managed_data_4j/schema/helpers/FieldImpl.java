package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class FieldImpl implements Field {

    private Schema schema;
    private Klass owner;
    private String name;
    private Type type;
    private Boolean many;
    private Boolean optional;
    private Field inverse;

    public FieldImpl(
        Schema schema,
        Klass owner,
        String name,
        Type type,
        Boolean many,
        Boolean optional,
        Field inverse)
    {
        this.setSchema(schema);
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

    @Override
    public Klass owner(Klass... owner) {
        return this.getOwner();
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setOwner(Klass owner) {
        this.owner = owner;
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
