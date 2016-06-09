package nl.cwi.examples.ql.schema;

public interface Question extends Statement {

	enum Type {
		INT, BOOL, STRING
	}
	
	String name(String ...name);
	String label(String ...label);
	Type type(Type ... type);

}
