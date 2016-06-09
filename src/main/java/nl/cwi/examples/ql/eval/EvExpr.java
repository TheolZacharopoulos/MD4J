package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.Expr;
import nl.cwi.examples.ql.values.Value;

public interface EvExpr extends Expr {
	Value eval();
}
