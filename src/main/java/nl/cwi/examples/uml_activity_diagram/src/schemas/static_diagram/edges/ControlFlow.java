package schemas.static_diagram.edges;

import schemas.static_diagram.variables.BooleanVariable;

public interface ControlFlow extends ActivityEdge {
	BooleanVariable guard(BooleanVariable...guard);
}
