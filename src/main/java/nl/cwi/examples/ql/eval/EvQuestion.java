package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.Question;
import nl.cwi.examples.ql.values.Value;

public interface EvQuestion extends Question {
	Value value(Value ...value);
}
