package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

import java.util.List;

public interface OpaqueAction extends Action {
    @Contain
    List<Expression> expressions(Expression...expressions);
}
