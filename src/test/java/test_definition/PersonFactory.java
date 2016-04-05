package test_definition;

import test_definition.schemas.Address;
import test_definition.schemas.Person;

public interface PersonFactory {
    Person Person();
    Person Person(Integer age, String name);

    Address Address();
    Address Address(String city, Integer number, String street);
}
