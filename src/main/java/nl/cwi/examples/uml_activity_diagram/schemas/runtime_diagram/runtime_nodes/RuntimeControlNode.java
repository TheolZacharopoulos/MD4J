package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;

import java.util.List;

public interface RuntimeControlNode extends RuntimeActivityNode {

    default boolean isReady() {
        return RuntimeActivityNode.super.isReady() && hasOffers();
    }

    default void fire(List<Token> tokens) {
        addTokens(tokens);
        sendOffers(tokens);
    }
}
