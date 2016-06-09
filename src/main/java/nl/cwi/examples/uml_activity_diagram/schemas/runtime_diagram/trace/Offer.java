package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace;

import nl.cwi.managed_data_4j.M;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public interface Offer extends M {

	List<Token> offeredTokens(Token...tokens);

	default boolean hasTokens() {
		removeWithdrawnTokens();
		return offeredTokens().size() > 0;
	}

	default void removeWithdrawnTokens() {
		List<Token> tokensToBeRemoved = new ArrayList<Token>();
		for(Token token : offeredTokens()) {
			if (token.isWithdrawn()) {
				tokensToBeRemoved.add(token);
			}
		}

		List<Token> tokenList = this.offeredTokens();
		tokenList.removeAll(tokensToBeRemoved);
		this.offeredTokens(tokenList.toArray(new Token[tokenList.size()]));
	}
	
}
