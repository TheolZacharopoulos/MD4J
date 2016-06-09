package schemas.runtime_diagram.runtime_nodes;

import schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import schemas.runtime_diagram.tokens.Token;
import schemas.static_diagram.nodes.ActivityFinalNode;

import java.util.List;

public interface RuntimeActivityFinalNode extends RuntimeFinalNode, ActivityFinalNode {
    default void fire(List<Token> tokens) {
        ((RuntimeActivity) activity()).terminateNodes();
    }
}
