package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Schema;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 */
public class Factory {

    private final Schema schema;

    public Factory(Schema _schema) {
        this.schema = _schema;
    }
}
