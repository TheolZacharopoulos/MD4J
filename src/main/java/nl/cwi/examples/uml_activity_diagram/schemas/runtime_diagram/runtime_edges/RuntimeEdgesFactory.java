package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.EdgesFactory;

public interface RuntimeEdgesFactory extends EdgesFactory {
    RuntimeActivityEdge ActivityEdge();
    RuntimeControlFlow ControlFlow();
}
