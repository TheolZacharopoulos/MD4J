package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeActivityNode extends ActivityNode {

    @Contain
    @Inverse(other = Token.class, field = "holder")
    List<Token> heldTokens(Token... tokens);

    Boolean running(Boolean... running);

    default void fire(List<Token> tokens) {
    }

    default void run() {
        running(true);
    }

    default boolean isRunning() {
        return running();
    }

    default void terminate() {
        running(false);
    }

    default boolean isReady() {
        return isRunning();
    }

    default void sendOffers(List<Token> tokens) {
        for (ActivityEdge edge : outgoing()) {
            ((RuntimeActivityEdge)edge).sendOffer(tokens);
        }
    }

    default List<Token> takeOfferdTokens() {
        List<Token> allTokens = new ArrayList<Token>();
        for (ActivityEdge edge : incoming()) {
            List<Token> tokens = ((RuntimeActivityEdge)edge).takeOfferedTokens();

            for (Token token : tokens) {
                token.withdraw();
            }
            allTokens.addAll(tokens);
        }
        return allTokens;
    }

    default void addTokens(List<Token> tokens) {
        for (Token token : tokens) {
            Token transferredToken = token.transfer(this);
            heldTokens().add(transferredToken);
        }
    }

    default boolean hasOffers() {
        boolean hasOffer = true;
        for (ActivityEdge edge : incoming()) {
            if(!((RuntimeActivityEdge)edge).hasOffer()) {
                hasOffer = false;
            }
        }
        return hasOffer;
    }

    default void removeToken(Token token) {
        heldTokens().remove(token);
    }
}
