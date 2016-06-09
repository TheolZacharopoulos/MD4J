package schemas.runtime_diagram.runtime_nodes;

import schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import schemas.static_diagram.edges.ActivityEdge;
import schemas.static_diagram.nodes.JoinNode;

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
