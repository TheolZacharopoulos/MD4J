package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.OpaqueAction;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

import java.util.List;

public interface RuntimeOpaqueAction extends RuntimeAction, OpaqueAction {
    @Contain
    List<Expression> expressions(Expression...expressions);

    @Override
    default void doAction() {
        for (Expression e : expressions()) {
            e.execute();
        }
    }
}
