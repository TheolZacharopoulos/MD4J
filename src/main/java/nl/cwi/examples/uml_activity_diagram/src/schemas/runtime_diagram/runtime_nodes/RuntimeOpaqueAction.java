package schemas.runtime_diagram.runtime_nodes;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import schemas.static_diagram.expressions.Expression;
import schemas.static_diagram.nodes.OpaqueAction;

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
