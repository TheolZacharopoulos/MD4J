package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables;

import nl.cwi.managed_data_4j.IFactory;

public interface VariablesFactory extends IFactory {
    IntegerVariable IntegerVariable();
    BooleanVariable BooleanVariable();
}
