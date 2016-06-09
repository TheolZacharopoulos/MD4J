package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.JoinNode;

public interface RuntimeJoinNode extends RuntimeControlNode, JoinNode {

    @Override
    default boolean isReady() {
        boolean ready = true;
        for (ActivityEdge edge : incoming()) {
            if (!((RuntimeActivityEdge)edge).hasOffer())
                ready = false;
        }
        return ready;
    }
}
