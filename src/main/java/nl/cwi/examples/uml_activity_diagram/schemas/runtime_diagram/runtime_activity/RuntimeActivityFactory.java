package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;

public interface RuntimeActivityFactory extends ActivityFactory {
    RuntimeActivity Activity();
}
