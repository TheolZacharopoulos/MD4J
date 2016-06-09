package nl.cwi.examples.ql.schema;

import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.examples.ql.values.Value;
import static nl.cwi.examples.ql.schema.Question.Type;

public interface QLPrimitives extends Primitives {
	Value Value();
	Type Type();
}
