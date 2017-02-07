package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Offer;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeActivityEdge extends ActivityEdge {

    @Contain
    List<Offer> offers(Offer...offers);

    default void sendOffer(List<Token> tokens) {
        Offer offer = FactoriesProvider.getRuntimeActivityDiagramFactory().Offer();

        for (Token token : tokens) {
            offer.offeredTokens().add(token);
        }

        offers().add(offer);
    }

    default List<Token> takeOfferedTokens() {
        List<Token> tokens = new ArrayList<Token>();

        for (Offer o : offers()) {
            tokens.addAll(o.offeredTokens());
        }
        offers().clear();

        return tokens;
    }

    default boolean hasOffer() {
        for (Offer o : offers()) {
            if (o.hasTokens()) {
                return true;
            }
        }
        return false;
    }
}
