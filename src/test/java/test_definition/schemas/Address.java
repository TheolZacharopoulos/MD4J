package test_definition.schemas;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;

public interface Address extends M {
    String street(String... street);
    Integer number(Integer... number);
    String city(String... city);

    @Inverse(other = Person.class, field = "address")
    Person tenant(Person... tenant);
}
