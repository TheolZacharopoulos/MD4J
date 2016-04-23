package nl.cwi.managed_data_4j.language.primitives;

public class intPrimitive extends AbstractPrimitive {
    public intPrimitive() {
        super(
            0,
            int.class,
            "int",
            (o1, o2) -> ((Integer) o1).compareTo((Integer) o2));
    }
}
