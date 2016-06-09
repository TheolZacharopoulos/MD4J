package schemas.runtime_diagram.tokens;

public interface ForkedToken extends Token {
	Token baseToken(Token... baseToken);
	Integer remainingOffersCount(Integer... remainingOffersCount);
}