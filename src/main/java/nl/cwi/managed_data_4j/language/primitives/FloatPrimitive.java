package nl.cwi.managed_data_4j.language.primitives;

public class FloatPrimitive extends AbstractPrimitive {
    public FloatPrimitive() {
        super(
            0.0,
            Float.class,
            "Float",
            (o1, o2) -> ((Float) o1).compareTo((Float) o2));
    }
}
