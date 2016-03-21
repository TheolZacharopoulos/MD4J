package nl.cwi.managed_data_4j.language.schema.models.implementation;

import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents an implementation of a Klass Schema definition.
 * @author Theologos Zacharopoulos
 */
public class KlassImpl implements Klass {

    protected String name = null;
    protected Schema schema = null;
    protected Set<Field> fields = Collections.emptySet();
    protected Set<Klass> supers = Collections.emptySet();
    protected Set<Klass> subklasses = Collections.emptySet();
    protected Class<?> classOf = null;
    protected Field key = null;

    public KlassImpl(String name) {
        this(name, null, null, null, null, null);
    }

    public KlassImpl(
            String name,
            Schema schema,
            Set<Field> fields,
            Set<Klass> supers,
            Set<Klass> subklasses,
            Class<?> classOf)
    {
        this.name = name;
        this.fields = fields;
        this.supers = supers;
        this.subklasses = subklasses;
        this.schema = schema;
        this.classOf = classOf;
    }

    @Override
    public Set<Field> fields(Field... field) {
        if (field.length > 0) {
            if (field.length > 1) {
                this.fields = new LinkedHashSet<>();
                for (Field aField : field) {
                    this.fields.add(aField);
                }
            } else {
                this.fields = Collections.singleton(field[0]);
            }
        }
        return this.fields;
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        if (supers.length > 0) {
            if (supers.length > 1) {
                this.supers = new LinkedHashSet<>();
                for (Klass aSuper : supers) {
                    this.supers.add(aSuper);
                }
            } else {
                this.supers = Collections.singleton(supers[0]);
            }
        }
        return this.supers;
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        if (subklasses.length > 0) {
            if (subklasses.length > 1) {
                this.subklasses = new LinkedHashSet<>();
                for (Klass aSuper : subklasses) {
                    this.subklasses.add(aSuper);
                }
            } else {
                this.subklasses = Collections.singleton(subklasses[0]);
            }
        }
        return this.subklasses;
    }

    @Override
    public String name(String... name) {
        if (name.length > 0) {
            this.name = name[0];
        }
        return this.name;
    }

    @Override
    public Schema schema(Schema... schema) {
        if (schema.length > 0) {
            this.schema = schema[0];
        }
        return this.schema;
    }

    @Override
    public Class<?> classOf(Class<?>... classOf) {
        if (classOf.length > 0) {
            this.classOf = classOf[0];
        }
        return this.classOf;
    }

    @Override
    public Field key(Field... key) {
        if (key.length > 0) {
            this.key = key[0];
        }
        return this.key;
    }
}
