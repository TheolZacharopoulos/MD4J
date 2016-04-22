package nl.cwi.managed_data_4j.language.primitives;

public class BooleanPrimitive extends AbstractPrimitive {
    public BooleanPrimitive() {
        super(
            false,
            Boolean.class,
            "Boolean",
            (o1, o2) -> ((Boolean) o1).compareTo((Boolean) o2));
    }
}
