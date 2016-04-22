package nl.cwi.managed_data_4j.language.primitives;

public class ClassPrimitive extends AbstractPrimitive {
    public ClassPrimitive() {
        super(
            null,
            Class.class,
            "Class",
            (o1, o2) -> ((Class) o1).getSimpleName().compareTo(((Class) o2).getSimpleName()));
    }
}
