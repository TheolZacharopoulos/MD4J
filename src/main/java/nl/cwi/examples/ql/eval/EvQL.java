package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.QL;

public interface EvQL extends QL {
	EvQuestion Question();
	//Computed Computed();
	
	EvBinOp BinOp();
	EvLit Lit();
}
