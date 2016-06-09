package schemas.runtime_diagram.runtime_nodes;

import schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import schemas.static_diagram.edges.ActivityEdge;
import schemas.static_diagram.nodes.MergeNode;

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
