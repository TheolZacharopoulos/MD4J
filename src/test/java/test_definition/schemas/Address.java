package test_definition.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional;

public interface Address extends M {
    String street(String... street);
    Integer number(Integer... number);
    String city(String... city);

    // Cross reference
    @Optional
    Person person(Person... person);
}
