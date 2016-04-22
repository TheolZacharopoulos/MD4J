package nl.cwi.managed_data_4j.language.primitives;

public class IntegerPrimitive extends AbstractPrimitive {
    public IntegerPrimitive() {
        super(
            0,
            Integer.class,
            "Integer",
            (o1, o2) -> ((Integer) o1).compareTo((Integer) o2));
    }
}
