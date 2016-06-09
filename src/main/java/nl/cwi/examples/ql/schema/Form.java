package nl.cwi.examples.ql.schema;

import java.util.List;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Form extends M {
	String name(String ...name);
	@Contain
	List<Statement> body(Statement ...stms);
}
