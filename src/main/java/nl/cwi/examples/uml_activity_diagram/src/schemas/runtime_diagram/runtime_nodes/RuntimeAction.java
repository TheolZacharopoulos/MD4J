package schemas.runtime_diagram.runtime_nodes;

import helpers.FactoriesProvider;
import schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import schemas.runtime_diagram.tokens.Token;
import schemas.static_diagram.nodes.Action;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeAction extends RuntimeExecutableNode, Action {

	default void doAction() {
		
	}

	default void fire(List<Token> tokens) {
		doAction();
		sendOffers();
	}

	default void sendOffers() {
		if (outgoing().size() > 0) {
			List<Token> tokens = new ArrayList<Token>();
			tokens.add(FactoriesProvider.getTokenFactory().ControlToken());
			addTokens(tokens);
			((RuntimeActivityEdge) outgoing().get(0)).sendOffer(tokens);
		}
	}

	default boolean isReady() {
		return RuntimeExecutableNode.super.isReady() && hasOffers();
	}
}
