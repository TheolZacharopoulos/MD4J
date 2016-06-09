package nl.cwi.examples.ql.schema;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface BinOp extends Expr {
	@Contain
	Expr lhs(Expr ...lhs);
	
	@Contain
	Expr rhs(Expr ...rhs);
	String op(String ...op);
}
