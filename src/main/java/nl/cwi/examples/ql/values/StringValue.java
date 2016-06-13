package nl.cwi.examples.ql.values;

public class StringValue extends Value {
	private final String value;

	public StringValue(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
