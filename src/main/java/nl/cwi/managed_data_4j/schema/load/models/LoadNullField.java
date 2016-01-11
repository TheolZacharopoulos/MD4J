package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.AbstractField;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class LoadNullField extends AbstractField {

    public static final String NAME = "NULL";

    public LoadNullField() {
        this(null, null);
    }

    public LoadNullField(Schema schema, Klass owner) {
        super(schema, owner);
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Type type(Type... type) {
        return null;
    }

    @Override
    public Boolean many(Boolean... many) {
        return null;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return null;
    }

    @Override
    public Field inverse(Field... field) {
        return null;
    }

    @Override
    public Klass owner(Klass... owner) {
        return this.owner;
    }

    public Schema getSchema() {
        return schema;
    }
}
