import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import org.junit.Before;
import org.junit.Test;
import test_definition.PersonFactory;
import test_definition.schemas.Address;
import test_definition.schemas.Car;
import test_definition.schemas.Person;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSchemaDefinition {

    private PersonFactory personFactory;

    @Before
    public void setup() {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final Schema personSchema = SchemaLoader.load(schemaFactory, schemaSchema, Person.class, Address.class, Car.class, Primitives.class);
        final BasicDataManager basicFactoryForPersons = new BasicDataManager(PersonFactory.class, personSchema);

        personFactory = basicFactoryForPersons.make();
    }

    @Test
    public void initial_values_Test() {
        Person person = personFactory.Person();

        assertEquals("", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());
    }

    @Test
    public void initialize_name_Test() {
        Person person = personFactory.Person();
        person.name("Alex");

        assertEquals("Alex", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());
    }

    @Test
    public void initialize_age_Test() {
        Person person = personFactory.Person();
        person.age(26);

        assertEquals("", person.name());
        assertEquals(new Integer(26), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());
    }

    @Test
    public void initialize_address_Test() {
        Person person = personFactory.Person();

        Address address = personFactory.Address();
        address.city("Amsterdam");

        person.address(address);

        assertEquals("", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(address.city(), person.address().city());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());
    }

    @Test
    public void initialize_grades_Test() {
        Person person = personFactory.Person();
        person.grades(1, 2);

        assertEquals("", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(2, person.grades().size());
        assertTrue(person.grades().contains(1));
        assertTrue(person.grades().contains(2));

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());
    }

    @Test
    public void initialize_list_Test() {
        Person person = personFactory.Person();

        Person friend1 = personFactory.Person();
        friend1.name("George");

        Person friend2 = personFactory.Person();
        friend1.name("Nick");
        person.friends(friend1, friend2);

        assertEquals("", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(2, person.friends().size());
    }

    @Test
    public void initialize_set_Test() {
        Person person = personFactory.Person();


        Car car1 = personFactory.Car("Tesla");
        Car car2 = personFactory.Car("Ford");

        person.cars(car1, car2);

        assertEquals("", person.name());
        assertEquals(new Integer(0), person.age());
        assertEquals(null, person.address());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(0, person.grades().size());

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(0, person.friends().size());

        assertThat(person.cars(), instanceOf(Set.class));
        assertEquals(2, person.cars().size());
    }

    @Test
    public void initialize_all_Test() {
        Person person = personFactory.Person(26, "Alex");
        Person friend1 = personFactory.Person(25, "George");
        Person friend2 = personFactory.Person(27, "Nick");
        person.friends(friend1, friend2);

        Address address = personFactory.Address("Amsterdam", 242, "Science Park");
        person.address(address);

        person.grades(1, 2);

        assertEquals("Alex", person.name());
        assertEquals(new Integer(26), person.age());
        assertEquals(address.city(), person.address().city());
        assertEquals(address.number(), person.address().number());
        assertEquals(address.street(), person.address().street());

        assertThat(person.grades(), instanceOf(List.class));
        assertEquals(2, person.grades().size());
        assertTrue(person.grades().contains(1));
        assertTrue(person.grades().contains(2));

        assertThat(person.friends(), instanceOf(List.class));
        assertEquals(2, person.friends().size());
    }

    @Test
    public void structure_inverse_Test() {
        Person person = personFactory.Person(26, "Alex");
        Address address = personFactory.Address("Amsterdam", 242, "Science Park");

        // inverse
        address.tenant(person);

        assertEquals(person.name(), address.tenant().name());
        assertEquals(person.age(), address.tenant().age());

        assertEquals(person.address().street(), address.street());
        assertEquals(person.address().city(), address.city());
        assertEquals(person.address().number(), address.number());

        Address address2 = personFactory.Address("Athens", 22, "Kolonaki");

        // change inverse
        address2.tenant(person);

        assertEquals(person.address().street(), address2.street());
        assertEquals(person.address().city(), address2.city());
        assertEquals(person.address().number(), address2.number());
    }

    @Test
    public void structure_inverse_many_Test() {
        Person person = personFactory.Person(26, "Alex");
        Car car1 = personFactory.Car("Tesla");
        Car car2 = personFactory.Car("Ford");

        // multi inverse
        car1.owner(person);
        car2.owner(person);

        assertEquals(2, person.cars().size());

        // add in inverse
        Car car3 = personFactory.Car("Nissan");
        car3.owner(person);

        assertEquals(3, person.cars().size());

        // remove in inverse
        Person person2 = personFactory.Person(25, "George");
        car3.owner(person2);

        assertEquals(2, person.cars().size());
    }

    @Test
    public void initial_null_values_Test() {
        Person person = personFactory.Person();

        person.name("Alex");
        assertEquals("Alex", person.name());

        person.name(null);
        assertEquals(null, person.name());

        person.grades(1, 2);
        assertEquals(2, person.grades().size());

        person.grades(null);
        assertEquals(0, person.grades().size());
    }

    @Test
    public void default_method_invocation_Test() {
        Person person = personFactory.Person();
        person.name("Alex");

        assertEquals("__Alex__", person.getNameWithFormat());
        assertEquals("Hello", person.justReturnWhatYouGet("Hello"));
    }
}
