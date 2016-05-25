package test_definition;

import nl.cwi.java_managed_data.IFactory;
import test_definition.schemas.Address;
import test_definition.schemas.Car;
import test_definition.schemas.Person;

public interface PersonFactory extends IFactory {
    Person Person();
    Person Person(Integer age, String name);

    Address Address();
    Address Address(String city, Integer number, String street);

    Car Car();
    Car Car(String brand);
}
