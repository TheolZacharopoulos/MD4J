package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.OpaqueAction;

public interface RuntimeOpaqueAction extends RuntimeAction, OpaqueAction {

    @Override
    default void doAction() {
        for (Expression e : expressions()) {
            e.execute();
        }
    }
}
