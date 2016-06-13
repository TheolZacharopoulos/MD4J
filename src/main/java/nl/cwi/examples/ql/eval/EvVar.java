package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.Var;
import nl.cwi.examples.ql.values.Value;

public interface EvVar extends EvExpr, Var {
	@Override
	default Value eval() {
		return ((EvQuestion)ref()).value();
	}
}
