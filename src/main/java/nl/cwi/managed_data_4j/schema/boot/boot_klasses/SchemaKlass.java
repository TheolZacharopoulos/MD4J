package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.TypesField;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

import java.util.Collections;
import java.util.Set;

public class SchemaKlass implements Klass {

    private Schema schema;

    public SchemaKlass(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return Schema.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {
        final Field typesField = new TypesField(schema, this);
        return Collections.singleton(typesField);
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.emptySet();
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return Collections.emptySet();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
