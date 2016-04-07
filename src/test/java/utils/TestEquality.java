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
        Person person1 = personFactory.Person();
        Person person2 = personFactory.Person();

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test() {
        Person person1 = personFactory.Person(25, "Alex");
        Person person2 = personFactory.Person(25, "Alex");

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test_fail() {
        Person person1 = personFactory.Person(25, "Alex1");
        Person person2 = personFactory.Person(25, "Alex");

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void primitives_only_equality_Test_fail2() {
        Person person1 = personFactory.Person(25, "Alex");
        Person person2 = personFactory.Person(24, "Alex");

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_Test() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_Test_1_fail_1() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 666, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void contain_equality_Test_1_fail_2() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(25, "Alex");
        person2.address(addressPerson2);

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_Test() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.person(person2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_Test_fail() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");

        person1.address(addressPerson1);
        addressPerson1.person(person1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");

        person2.address(addressPerson2);
        addressPerson2.person(person1); // wrong person reference

        assertFalse(MObjectUtils.equals(person1, person2));
    }
}