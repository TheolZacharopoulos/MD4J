package nl.cwi.managed_data_4j.language.primitives;

public class DoublePrimitive extends AbstractPrimitive {
    public DoublePrimitive() {
        super(
            0.0,
            Double.class,
            "Double",
            (o1, o2) -> ((Double) o1).compareTo((Double) o2));
    }
}
