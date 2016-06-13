package test_definition;

import test_definition.schemas.APerson;

public interface APersonFactory extends PersonFactory {
    APerson Person();
}
