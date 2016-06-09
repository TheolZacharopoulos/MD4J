package schemas.runtime_diagram.tokens;

import nl.cwi.managed_data_4j.IFactory;
import schemas.runtime_diagram.trace.Offer;

public interface TokenFactory extends IFactory {
    Token Token();
    ForkedToken ForkedToken();
    ControlToken ControlToken();

    Offer Offer();
}
