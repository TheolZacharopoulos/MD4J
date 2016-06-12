package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;

import java.util.List;

public interface RuntimeControlNode extends RuntimeActivityNode {

    @Override
    default void fire(List<Token> tokens) {
        System.out.println("Firing: RuntimeControlNode");
        addTokens(tokens);
        sendOffers(tokens);
    }

    @Override
    default boolean isReady() {
        return isRunning() && hasOffers();
    }
}
