package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.MergeNode;

public interface RuntimeMergeNode extends RuntimeControlNode, MergeNode {
    @Override
    default boolean hasOffers() {
        for (ActivityEdge edge : incoming()) {
            if (((RuntimeActivityEdge) edge).hasOffer()) {
                return true;
            }
        }
        return false;
    }
}
