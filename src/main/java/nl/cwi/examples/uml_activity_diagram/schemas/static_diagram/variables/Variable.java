package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;

public interface Variable extends M {
	String name(String... name);

	@Contain
	Value initialValue(Value... initialValue);

	Value currentValue(Value... currentValue);
}
