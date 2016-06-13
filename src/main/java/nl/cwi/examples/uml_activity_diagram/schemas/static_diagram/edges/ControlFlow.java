package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public interface ControlFlow extends ActivityEdge {
	BooleanVariable guard(BooleanVariable...guard);
}
