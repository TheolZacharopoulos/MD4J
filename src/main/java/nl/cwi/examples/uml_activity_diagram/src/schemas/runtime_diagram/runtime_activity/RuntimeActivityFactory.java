package schemas.runtime_diagram.runtime_activity;

import schemas.static_diagram.activity.ActivityFactory;

public interface RuntimeActivityFactory extends ActivityFactory {
    RuntimeActivity Activity();
}
