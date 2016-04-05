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
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final Schema personSchema = SchemaLoader.load(schemaFactory, schemaSchema, Person.class, Address.class);
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
    public void primitives_only_equality_real_object_Test_fail() {
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

    @Test
    public void contain_equality_Test() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.person(24, "Alex");
        person2.address(addressPerson2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_real_object_Test() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);

        Person person2 = new Person() {
            @Override
            public String name(String... name) { return "Alex"; }
            @Override
            public Integer age(Integer... age) { return 24; }
            @Override
            public Address address(Address... address) { return new Address() {
                @Override
                public String street(String... street) { return "Science Park"; }
                @Override
                public Integer number(Integer... number) { return 242; }
                @Override
                public String city(String... city) { return "Amsterdam"; }
                @Override
                public Person person(Person... person) { return null; }
            }; }
        };

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_real_object_Test_1_fail() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.address("Amsterdam", 666, "Science Park");
        Person person2 = personFactory.person(24, "Alex");
        person2.address(addressPerson2);

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_real_object_Test_1_fail_1() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.person(25, "Alex");
        person2.address(addressPerson2);

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_Test() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.person(person2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_real_object_Test() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = new Address() {
            @Override
            public String street(String... street) { return "Science Park"; }
            @Override
            public Integer number(Integer... number) { return 242; }
            @Override
            public String city(String... city) { return "Amsterdam"; }
            @Override
            public Person person(Person... person) { return null; }
        };

        Person person2 = new Person() {
            @Override
            public String name(String... name) { return "Alex"; }
            @Override
            public Integer age(Integer... age) { return 24; }
            @Override
            public Address address(Address... address) { return addressPerson2; }
        };

        addressPerson2.person(person2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_Test_fail() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.person(person1); // wrong person reference

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_real_object_Test_fail() {
        Address addressPerson1 = personFactory.address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = new Address() {
            @Override
            public String street(String... street) { return "Science Park"; }
            @Override
            public Integer number(Integer... number) { return 242; }
            @Override
            public String city(String... city) { return "Amsterdam"; }
            @Override
            public Person person(Person... person) { return null; }
        };

        Person person2 = new Person() {
            @Override
            public String name(String... name) { return "Alex"; }
            @Override
            public Integer age(Integer... age) { return 24; }
            @Override
            public Address address(Address... address) { return addressPerson2; }
        };

        addressPerson2.person(person1); // wrong person reference

        assertFalse(MObjectUtils.equals(person1, person2));
    }
}
