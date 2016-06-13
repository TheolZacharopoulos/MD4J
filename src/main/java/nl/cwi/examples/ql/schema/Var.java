package nl.cwi.examples.ql.schema;

public interface Var extends Expr {
	Question ref(Question ...q);
}
