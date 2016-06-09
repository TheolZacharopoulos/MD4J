package schemas.runtime_diagram.runtime_edges;

import schemas.static_diagram.edges.EdgesFactory;

public interface RuntimeEdgesFactory extends EdgesFactory {
    RuntimeActivityEdge ActivityEdge();
    RuntimeControlFlow ControlFlow();
}
