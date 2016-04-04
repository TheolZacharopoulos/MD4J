package test_definition.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Person {

    String name(String... name);
    Integer age(Integer... age);

    @Contain
    Address address(Address... address);
}
