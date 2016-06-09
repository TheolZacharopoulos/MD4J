package nl.cwi.examples.ql.schema;

import java.util.List;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Block extends Statement {

	@Contain
	List<Statement> body(Statement...s);
}
