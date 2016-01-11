package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.implementation.AbstractKlass;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.util.Set;

public class LoadKlass extends AbstractKlass {

    private String name;
    private Set<Klass> supers;
    private Set<Klass> subklasses;
    private Set<Field> fields;

    public LoadKlass(Schema schema) {
        super(schema);
    }

    public LoadKlass(
        String name,
        Schema schema,
        Set<Klass> supers,
        Set<Klass> subklasses,
        Set<Field> fields)
    {
        super(schema);
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
