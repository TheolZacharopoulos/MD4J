package test_definition.schemas;

public interface APerson extends Person {

    @Override
    default String getNameWithFormat() {
        return "%%" + name() + "%%";
    }
}
