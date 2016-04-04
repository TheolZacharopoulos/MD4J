package utils;

import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import test_definition.PersonFactory;
import test_definition.schemas.Address;
import test_definition.schemas.Person;

import static org.junit.Assert.*;

public class TestEquality {

    private PersonFactory personFactory;

    @Before
    public void setup() {
        PropertyConfigurator.configure("src/test/resources/logger.properties");

        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();
        final Schema personSchema = SchemaLoader.load(schemaFactory, Person.class, Address.class);
        final BasicFactory basicFactoryForPersons = new BasicFactory(PersonFactory.class, personSchema);

        personFactory = basicFactoryForPersons.make();
    }

    @Test
    public void null_values_equality_Test() {
        Person person1 = personFactory.person();
        Person person2 = personFactory.person();

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test() {
        Person person1 = personFactory.person(25, "Alex");
        Person person2 = personFactory.person(25, "Alex");

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_with_real_object_Test() {
        Person person1 = personFactory.person(25, "Alex");
        Person person2 = new Person() {
            @Override
            public String name(String... name) { return "Alex"; }
            @Override
            public Integer age(Integer... age) { return 25; }
            @Override
            public Address address(Address... address) { return null; }
        };

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test_fail() {
        Person person1 = personFactory.person(25, "Alex1");
        Person person2 = personFactory.person(25, "Alex");

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test_fail2() {
        Person person1 = personFactory.person(25, "Alex");
        Person person2 = personFactory.person(24, "Alex");

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality__real_object_Test_fail() {
        Person person1 = personFactory.person(25, "Alex");
        Person person2 = new Person() {
            @Override
            public String name(String... name) { return "Alex"; }
            @Override
            public Integer age(Integer... age) { return 26; }
            @Override
            public Address address(Address... address) { return null; }
        };

        assertFalse(MObjectUtils.equals(person1, person2));
    }
}
