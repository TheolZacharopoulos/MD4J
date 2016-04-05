package test_definition.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Person extends M {

    String name(String... name);
    Integer age(Integer... age);

    @Contain
    Address address(Address... address);
}
