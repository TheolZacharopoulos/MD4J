package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Offer;
import nl.cwi.managed_data_4j.IFactory;

public interface TokenFactory extends IFactory {
    Token Token();
    ForkedToken ForkedToken();
    ControlToken ControlToken();

    Offer Offer();
}
