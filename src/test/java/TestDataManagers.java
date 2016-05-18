import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import org.junit.Before;
import org.junit.Test;
import test_definition.PersonFactory;
import test_definition.data_manager.TestDataManager;
import test_definition.schemas.Address;
import test_definition.schemas.Car;
import test_definition.schemas.Person;

import static org.junit.Assert.assertEquals;

public class TestDataManagers {

    private PersonFactory personFactory;

    @Before
    public void setup() {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final Schema personSchema = SchemaLoader.load(
                schemaFactory, schemaSchema, Person.class, Address.class, Car.class, Primitives.class);

        final TestDataManager testDataManager = new TestDataManager();
        personFactory = testDataManager.factory(PersonFactory.class, personSchema);
    }

    @Test
    public void set_vale_double_Test() {
        Person person = personFactory.Person();
        person.value(44);
        person.name("Alex");

        assertEquals(44*2, person.value());
        assertEquals("Alex", person.name());
    }

    @Test
    public void get_age_666_Test() {
        Person person = personFactory.Person();
        person.age(44);
        person.name("Alex");

        assertEquals(new Integer(666), person.age());
        assertEquals("Alex", person.name());
    }

    @Test
    public void change_state_on_default_666_Test() {
        Person person = personFactory.Person();
        person.justReturnWhatYouGet("NewName");

        assertEquals("NewName1", person.name());
    }
}
