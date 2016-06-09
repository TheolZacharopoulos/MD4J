package schemas.runtime_diagram.runtime_nodes;

import helpers.FactoriesProvider;
import schemas.runtime_diagram.tokens.ForkedToken;
import schemas.runtime_diagram.tokens.Token;
import schemas.static_diagram.nodes.ForkNode;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeForkNode extends RuntimeControlNode, ForkNode {

    @Override
    default void fire(List<Token> tokens) {
        List<Token> forkedTokens = new ArrayList<Token>();
        for(Token token : tokens) {
            ForkedToken forkedToken = FactoriesProvider.getTokenFactory().ForkedToken();

            forkedToken.baseToken(token);
            forkedToken.remainingOffersCount(outgoing().size());
            forkedTokens.add(forkedToken);
        }
        addTokens(forkedTokens);
        sendOffers(forkedTokens);
    }
}
