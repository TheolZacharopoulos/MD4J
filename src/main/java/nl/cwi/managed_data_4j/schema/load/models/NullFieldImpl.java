package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class NullFieldImpl implements Field {

    public static final String NAME = "NULL";

    private final Schema schema;
    private final Klass owner;

    public NullFieldImpl() {
        this(null, null);
    }

    public NullFieldImpl(Schema schema, Klass owner) {
        this.schema = schema;
        this.owner = owner;
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
