package test_definition.schemas;

public interface Address {
    String street(String... street);
    Integer number(Integer... number);
    String city(String... city);
}
