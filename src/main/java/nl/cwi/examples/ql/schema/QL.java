package nl.cwi.examples.ql.schema;

import nl.cwi.managed_data_4j.IFactory;

public interface QL extends IFactory {
	Form Form();
	
	Block Block();
	Question Question();
	Computed Computed();
	IfThen IfThen();
	IfThenElse IfThenElse();
	
	BinOp BinOp();
	Lit Lit();
	Var Var();
}
