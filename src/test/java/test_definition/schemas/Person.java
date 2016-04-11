package test_definition.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

import java.util.List;
import java.util.Set;

public interface Person extends M {

    String name(String... name);
    Integer age(Integer... age);

    List<Integer> grades(Integer... grades);

    @Contain
    List<Person> friends(Person... friend);

    @Contain
    Address address(Address... address);
}
