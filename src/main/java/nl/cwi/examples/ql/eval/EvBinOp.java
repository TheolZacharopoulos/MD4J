package nl.cwi.examples.ql.eval;

import nl.cwi.examples.ql.schema.BinOp;
import nl.cwi.examples.ql.values.BoolValue;
import nl.cwi.examples.ql.values.IntValue;
import nl.cwi.examples.ql.values.Value;

public interface EvBinOp extends EvExpr, BinOp {
	default Value eval() {
		switch (op()) {
		case "+": return new IntValue(((IntValue)((EvExpr)lhs()).eval()).getValue() + ((IntValue)((EvExpr)rhs()).eval()).getValue());
		case "-": return new IntValue(((IntValue)((EvExpr)lhs()).eval()).getValue() - ((IntValue)((EvExpr)rhs()).eval()).getValue());
		case "*": return new IntValue(((IntValue)((EvExpr)lhs()).eval()).getValue() * ((IntValue)((EvExpr)rhs()).eval()).getValue());
		case "/": return new IntValue(((IntValue)((EvExpr)lhs()).eval()).getValue() / ((IntValue)((EvExpr)rhs()).eval()).getValue());
		case "&&": return new BoolValue(((BoolValue)((EvExpr)lhs()).eval()).getValue() && ((BoolValue)((EvExpr)rhs()).eval()).getValue());
		case "||": return new BoolValue(((BoolValue)((EvExpr)lhs()).eval()).getValue() || ((BoolValue)((EvExpr)rhs()).eval()).getValue());
		default: throw new RuntimeException("unsupported operator: " + op());
		}
	}
}
