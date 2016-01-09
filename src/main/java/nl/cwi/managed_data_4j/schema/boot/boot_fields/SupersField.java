package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_klasses.KlassKlass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class SupersField implements Field {

    public static final String NAME = "supers";

    private Schema schema;
    private Klass owner;

    public SupersField(Schema schema, Klass owner) {
        this.schema = schema;
        this.owner = owner;
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Type type(Type... type) {
        return new KlassKlass(schema);
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
        return new NullField();
    }

    @Override
    public Klass owner(Klass... owner) {
        return this.owner;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setOwner(Klass owner) {
        this.owner = owner;
    }
}
