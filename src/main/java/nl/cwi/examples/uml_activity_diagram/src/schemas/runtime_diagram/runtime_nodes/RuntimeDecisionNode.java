package schemas.runtime_diagram.runtime_nodes;

import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import schemas.runtime_diagram.runtime_edges.RuntimeControlFlow;
import schemas.runtime_diagram.tokens.Token;
import schemas.static_diagram.edges.ActivityEdge;
import schemas.static_diagram.nodes.DecisionNode;
import schemas.static_diagram.values.BooleanValue;
import schemas.static_diagram.values.Value;

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
