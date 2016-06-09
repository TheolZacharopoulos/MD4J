package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity;

import nl.cwi.examples.uml_activity_diagram.schemas.NamedElement;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Trace;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.Variable;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;

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
