package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_klasses.TypeKlass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class NullField implements Field {

    public static final String NAME = "NULL";

    private Schema schema;
    private Klass owner;

    public NullField() {
        this(null, null);
    }

    public NullField(Schema schema, Klass owner) {
        this.schema = schema;
        this.owner = owner;
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Type type(Type... type) {
        return new TypeKlass(schema);
    }

    @Override
    public Boolean many(Boolean... many) {
        return false;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return false;
    }

    @Override
    public Field inverse(Field... field) {
        return null;
    }

    @Override
    public Klass owner(Klass... owner) {
        return this.owner;
    }
}
