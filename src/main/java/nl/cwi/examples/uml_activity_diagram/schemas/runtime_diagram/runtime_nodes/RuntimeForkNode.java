package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ForkNode;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.ForkedToken;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeForkNode extends RuntimeControlNode, ForkNode {

    @Override
    default void fire(List<Token> tokens) {
        List<Token> forkedTokens = new ArrayList<Token>();
        for(Token token : tokens) {
            ForkedToken forkedToken = FactoriesProvider.getRuntimeActivityDiagramFactory().ForkedToken();

            forkedToken.baseToken(token);
            forkedToken.remainingOffersCount(outgoing().size());
            forkedTokens.add(forkedToken);
        }
        addTokens(forkedTokens);
        sendOffers(forkedTokens);
    }
}
