package com.cwi.managed_data.klass_system.helpers;

import com.cwi.managed_data.klass_system.factories.BootstrapFactory;
import com.cwi.managed_data.klass_system.factories.proxied.SchemaFactory;
import com.cwi.managed_data.klass_system.models.Schema;
import com.cwi.managed_data.klass_system.factories.Factory;

public class SchemaManager {

    private Schema schemaSchema;

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
