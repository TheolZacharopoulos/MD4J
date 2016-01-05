package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.util.Set;

public class KlassImpl implements Klass {

    private Schema schema;
    private String name;
    private Set<Klass> supers;
    private Set<Klass> subklasses;
    private Set<Field> fields;

    public KlassImpl(
        String name,
        Schema schema,
        Set<Klass> supers,
        Set<Klass> subklasses,
        Set<Field> fields)
    {
        this.setSchema(schema);
        this.name = name;
        this.supers = supers;
        this.subklasses = subklasses;
        this.fields = fields;
    }

    @Override
    public String name(String... name) {
        return this.name;
    }

    @Override
    public Set<Field> fields(Field...field) {
        return this.fields;
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return this.supers;
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return this.subklasses;
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public void setSupers(Set<Klass> supers) {
        this.supers = supers;
    }

    public void setSubklasses(Set<Klass> subklasses) {
        this.subklasses = subklasses;
    }

    public void setName(String name) {
        this.name = name;
    }
}
