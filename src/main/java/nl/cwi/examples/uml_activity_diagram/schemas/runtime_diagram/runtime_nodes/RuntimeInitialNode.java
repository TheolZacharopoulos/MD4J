package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.InitialNode;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeInitialNode extends RuntimeControlNode, InitialNode {

    @Override
    default void fire(List<Token> tokens) {
        System.out.println("Firing: Runtime InitialNode");
        List<Token> producedTokens = new ArrayList<Token>();
        producedTokens.add(FactoriesProvider.getRuntimeActivityDiagramFactory().ControlToken());

        addTokens(producedTokens);
        sendOffers(producedTokens);
    }

    @Override
    default boolean isReady() {
        return false; // is fired by activity directly
    }
}
