package nl.cwi.managed_data_4j.language.primitives;

import java.util.Comparator;

public abstract class AbstractPrimitive {
    protected Object defaultValue;
    protected Class<?> typeClass;
    protected String simpleName;
    protected Comparator<Object> comparator;

    public AbstractPrimitive(Object defaultValue, Class<?> typeClass, String simpleName, Comparator<Object> comparator) {
        this.defaultValue = defaultValue;
        this.typeClass = typeClass;
        this.simpleName = simpleName;
        this.comparator = comparator;
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

    public Comparator<Object> getComparator() {
        return comparator;
    }
}
