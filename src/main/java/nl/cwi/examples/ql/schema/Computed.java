package nl.cwi.examples.ql.schema;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Computed extends Question {

	@Contain
	Expr expr(Expr ...expr);
}
