package nl.cwi.managed_data_4j.language.primitives;

public class StringPrimitive extends AbstractPrimitive {
    public StringPrimitive() {
        super(
            "",
            String.class,
            "String",
            (o1, o2) -> ((String) o1).compareTo((String) o2));
    }
}
