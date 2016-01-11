package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses.KlassKlass;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;
import nl.cwi.managed_data_4j.schema.models.implementation.AbstractField;

public class SubKlassesField extends AbstractField {

    public static final String NAME = "subklasses";

    public SubKlassesField(Schema schema, Klass owner) {
        super(schema, owner);
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
    public Field inverse(Field... field) {
        return new SupersField(schema, owner);
    }
}
