package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.Action;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeAction extends RuntimeExecutableNode, Action {

	default void doAction() {}

	default void fire(List<Token> tokens) {
		doAction();
		sendOffers();
	}

	default void sendOffers() {
		if (outgoing().size() > 0) {
			List<Token> tokens = new ArrayList<Token>();
			tokens.add(FactoriesProvider.getRuntimeActivityDiagramFactory().ControlToken());
			addTokens(tokens);
			((RuntimeActivityEdge) outgoing().get(0)).sendOffer(tokens);
		}
	}

	default boolean isReady() {
		return isRunning() && hasOffers();
	}
}
