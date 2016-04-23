package nl.cwi.managed_data_4j.language.primitives;

public class boolPrimitive extends AbstractPrimitive {
    public boolPrimitive() {
        super(
            false,
            boolean.class,
            "boolean",
            (o1, o2) -> ((Boolean) o1).compareTo((Boolean) o2));
    }
}
