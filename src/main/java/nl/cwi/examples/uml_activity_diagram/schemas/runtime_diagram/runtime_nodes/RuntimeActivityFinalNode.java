package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityFinalNode;

import java.util.List;

public interface RuntimeActivityFinalNode extends RuntimeFinalNode, ActivityFinalNode {

    @Override
    default void fire(List<Token> tokens) {
        System.out.println("Firing: Runtime ActivityFinalNode ");
        ((RuntimeActivity) activity()).terminateNodes();
    }
}
