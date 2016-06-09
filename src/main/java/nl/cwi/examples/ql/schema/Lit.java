package nl.cwi.examples.ql.schema;

import nl.cwi.examples.ql.values.Value;

public interface Lit extends Expr {
	Value value(Value ...value);
}
