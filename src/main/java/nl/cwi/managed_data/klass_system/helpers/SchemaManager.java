package nl.cwi.managed_data.klass_system.helpers;

import nl.cwi.managed_data.klass_system.factories.BootstrapFactory;
import nl.cwi.managed_data.klass_system.factories.proxied.SchemaFactory;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.klass_system.factories.BootstrapFactory;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.klass_system.factories.proxied.SchemaFactory;
import nl.cwi.managed_data.klass_system.models.Schema;

public class SchemaManager {

    private Schema schemaSchema;

    private static final String SCHEMA_PATH = "nl/cwi/managed_data/klass_system/models/Schema.java";
    private static final String KLASS_PATH = "nl/cwi/managed_data/klass_system/models/Klass.java";
    private static final String FIELD_PATH = "nl/cwi/managed_data/klass_system/models/Field.java";

    public SchemaManager() {
        schemaSchema = load(Schema.class, new BootstrapFactory());
    }

    public Schema make(Class<?> _classSchema) {
        return load(_classSchema, new Factory(schemaSchema));
    }

    public Schema load(Class<?> _classSchema, Factory _factory) {
        // TODO:
        return SchemaFactory.make(_classSchema);
    }
}
