package schemas.runtime_diagram.runtime_edges;

import helpers.FactoriesProvider;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import schemas.runtime_diagram.tokens.Token;
import schemas.runtime_diagram.trace.Offer;
import schemas.static_diagram.edges.ActivityEdge;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeActivityEdge extends ActivityEdge {

    @Contain
    List<Offer> offers(Offer...offers);

    default List<Offer> getOffers() {
        return offers();
    }

    default void sendOffer(List<Token> tokens) {
        Offer offer = FactoriesProvider.getTokenFactory().Offer();

        List<Token> tokenList = offer.offeredTokens();
        for (Token token : tokens) {
            tokenList.add(token);
        }

        offer.offeredTokens(tokenList.toArray(new Token[tokenList.size()]));

        List<Offer> offers = getOffers();
        offers.add(offer);
        offers(offers.toArray(new Offer[offers.size()]));
    }

    default List<Token> takeOfferedTokens() {
        List<Token> tokens = new ArrayList<Token>();

        for (Offer o : getOffers()) {
            tokens.addAll(o.offeredTokens());
        }
//		getOffers().clear();
        offers(null);

        return tokens;
    }

    default boolean hasOffer() {
        for (Offer o : getOffers()) {
            if (o.hasTokens()) {
                return true;
            }
        }
        return false;
    }
}
