package nl.cwi.managed_data_4j.language.primitives;

public abstract class AbstractPrimitive {
    protected Object defaultValue;
    protected Class<?> typeClass;
    protected String simpleName;

    public AbstractPrimitive() {
        this(null, null, null);
    }

    public AbstractPrimitive(Object defaultValue, Class<?> typeClass, String simpleName) {
        this.defaultValue = defaultValue;
        this.typeClass = typeClass;
        this.simpleName = simpleName;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public String getSimpleName() {
        return simpleName;
    }
}
