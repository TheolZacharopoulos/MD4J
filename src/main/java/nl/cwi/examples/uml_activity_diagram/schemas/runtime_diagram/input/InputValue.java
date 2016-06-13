package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.input;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.Variable;
import nl.cwi.managed_data_4j.M;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;

public interface InputValue extends M {
	Value value(Value... value);

	default Value getValue() {
		return value();
	}

	default void setValue(Value value) {
		this.value(value);
	}

	Variable variable(Variable... variable);

	default Variable getVariable() {
		return variable();
	}

	default void setVariable(Variable variable) {
		this.variable(variable);
	}
}
