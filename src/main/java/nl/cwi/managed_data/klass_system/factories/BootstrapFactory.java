package nl.cwi.managed_data.klass_system.factories;

import nl.cwi.managed_data.klass_system.factories.proxied.SchemaFactory;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.klass_system.factories.proxied.SchemaFactory;
import nl.cwi.managed_data.klass_system.models.Schema;

/**
 * A factory that builds SchemaSchemas.
 * We don't use the Factory class because this will be circular:
 * Schema pointSchema = load(“path-to-interface-schema”, new Factory(schemaSchema));
 *
 * then use use it like this:
 * Schema schemaSchema = load(“path-to-interfaces-schema-klass-etc”, new BootstrapFactory()).
 *
 * The BootstrapFactory knows about schemas and just creates some implementation
 * conforming to the Schema, Klass etc. interfaces (no need for proxies or anything).
 */
public class BootstrapFactory extends Factory {

    // TODO:
    public BootstrapFactory() {
        super(SchemaFactory.make(Schema.class));
    }
}
