package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.DecisionNode;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;

import java.util.List;

public interface RuntimeDecisionNode extends RuntimeControlNode, DecisionNode {

    @Override
    default void fire(List<Token> tokens) {
        ActivityEdge selectedEdge = null;

        for (ActivityEdge edge : outgoing()) {

            if (MObjectUtils.instanceOf(edge, RuntimeControlFlow.class)) {
                RuntimeControlFlow controlFlow = (RuntimeControlFlow) edge;
                Value guardValue = controlFlow.guard().currentValue();

                if (MObjectUtils.instanceOf(guardValue, BooleanValue.class)) {
                    BooleanValue booleanValue = (BooleanValue) guardValue;
                    if (booleanValue.value()) {
                        selectedEdge = edge;
                        break;
                    }
                }
            }
        }

        if (selectedEdge != null) {
            addTokens(tokens);
            ((RuntimeActivityEdge)selectedEdge).sendOffer(tokens);
        }
    }

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
