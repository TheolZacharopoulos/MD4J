package schemas;

import nl.cwi.managed_data_4j.M;

public interface NamedElement extends M {
	String name(String...name);
}