package utils;

import org.junit.Before;
import org.junit.Test;

import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import test_definition.PersonFactory;
import test_definition.schemas.Address;
import test_definition.schemas.Car;
import test_definition.schemas.Person;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestEquality {

    private PersonFactory personFactory;

    @Before
    public void setup() {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final Schema personSchema = SchemaLoader.load(schemaFactory, Person.class, Address.class, Car.class, Primitives.class);
        final BasicDataManager basicFactoryForPersons = new BasicDataManager();

        personFactory = basicFactoryForPersons.factory(PersonFactory.class, personSchema);
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
        addressPerson1.tenant(person1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_Test_fail() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");

        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");

        person2.address(addressPerson2);
        addressPerson2.tenant(person1); // wrong tenant reference

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_primitive_set_ordered_Test() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_primitive_list_Test() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        person1.grades(new Integer[]{1,2,3});

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        person2.grades(new Integer[]{1,2,3});

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_primitive_list_Test_fail() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        person1.grades(new Integer[]{1,2,3});

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        person2.grades(new Integer[]{1, 3});

        assertFalse(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_klass_list_Test() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        Person person1Friend1 = personFactory.Person(20, "George");
        Person person1Friend2 = personFactory.Person(22, "Martin");

        Set<Person> person1Friends = new LinkedHashSet<>();
        person1Friends.add(person1Friend1);
        person1Friends.add(person1Friend2);

        person1.friends(person1Friends.toArray(new Person[person1Friends.size()]));

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        person2.friends(person1Friends.toArray(new Person[person1Friends.size()]));

        assertTrue(MObjectUtils.equals(person1, person2));
    }

    @Test
    public void cross_reference_equality_klass_list_Test_fail() {
        Address addressPerson1 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person1 = personFactory.Person(24, "Alex");
        person1.address(addressPerson1);
        addressPerson1.tenant(person1);

        Person person1Friend1 = personFactory.Person(20, "George");
        Person person1Friend2 = personFactory.Person(22, "Martin");

        person1.friends(new Person[]{person1Friend1, person1Friend2});

        Address addressPerson2 = personFactory.Address("Amsterdam", 242, "Science Park");
        Person person2 = personFactory.Person(24, "Alex");
        person2.address(addressPerson2);
        addressPerson2.tenant(person2);

        person2.friends(new Person[]{person1Friend2});

        assertFalse(MObjectUtils.equals(person1, person2));
    }
}
