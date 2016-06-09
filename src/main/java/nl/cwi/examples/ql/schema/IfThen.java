package nl.cwi.examples.ql.schema;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface IfThen extends Statement {

	@Contain
	Expr condition(Expr ...c);
	
	@Contain
	Statement then(Statement ...q);
}
