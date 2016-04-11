package nl.cwi.managed_data_4j.language.schema.models.implementation;

import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

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
    protected Set<Klass> subKlasses = Collections.emptySet();
    protected Class<?> classOf = null;
    protected Field key = null;
    protected Klass schemaKlass = null;

    public KlassImpl(String name) {
        this(name, null, null, null, null, null);
    }

    public KlassImpl(
            String name,
            Schema schema,
            Set<Field> fields,
            Set<Klass> supers,
            Set<Klass> subKlasses,
            Class<?> classOf)
    {
        this.name = name;
        this.fields = fields;
        this.supers = supers;
        this.subKlasses = subKlasses;
        this.schema = schema;
        this.classOf = classOf;
    }

    @Contain
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
    public Set<Klass> subKlasses(Klass... subKlasses) {
        if (subKlasses.length > 0) {
            if (subKlasses.length > 1) {
                this.subKlasses = new LinkedHashSet<>();
                for (Klass aSuper : subKlasses) {
                    this.subKlasses.add(aSuper);
                }
            } else {
                this.subKlasses = Collections.singleton(subKlasses[0]);
            }
        }
        return this.subKlasses;
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
    public Klass schemaKlass(Klass... schemaKlass) {
        if (schemaKlass.length > 0) {
            this.schemaKlass = schemaKlass[0];
        }
        return this.schemaKlass;
    }

    @Override
    public Field key(Field... key) {
        if (key.length > 0) {
            this.key = key[0];
        }
        return this.key;
    }
}
