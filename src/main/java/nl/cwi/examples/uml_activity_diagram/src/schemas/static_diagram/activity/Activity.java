package schemas.static_diagram.activity;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import schemas.NamedElement;
import schemas.runtime_diagram.trace.Trace;
import schemas.static_diagram.edges.ActivityEdge;
import schemas.static_diagram.nodes.ActivityNode;
import schemas.static_diagram.variables.Variable;

import java.util.List;

public interface Activity extends NamedElement {
	@Contain
	@Inverse(other = ActivityNode.class, field = "activity")
	List<ActivityNode> nodes(ActivityNode...nodes);

	@Contain
	List<ActivityEdge> edges(ActivityEdge...edges);

	@Contain
	List<Variable> locals(Variable...locals);

	@Contain
	List<Variable> inputs(Variable...inputs);

	@Contain
	Trace trace(Trace...trace);
}
