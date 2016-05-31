package test_definition.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;

public interface Car {
    @Key
    String brand(String... brand);

    @Inverse(other = Person.class, field = "cars")
    Person owner(Person... owner);
}
