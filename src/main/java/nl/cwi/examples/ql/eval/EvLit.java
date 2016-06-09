package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.Lit;
import nl.cwi.examples.ql.values.Value;

public interface EvLit extends EvExpr, Lit {
	@Override
	default Value eval() {
		return value();
	}
}
