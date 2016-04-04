package test_definition;

import test_definition.schemas.Address;
import test_definition.schemas.Person;

public interface PersonFactory {
    Person person();
    Person person(Integer age, String name);

    Address address();
    Address address(String city, Integer number, String street);
}
