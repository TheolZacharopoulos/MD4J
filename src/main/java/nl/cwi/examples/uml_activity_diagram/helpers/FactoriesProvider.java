package nl.cwi.examples.uml_activity_diagram.helpers;

import nl.cwi.examples.uml_activity_diagram.schemas.OperatorsPrimitives;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.input.InputValue;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeEdgesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.ControlToken;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.ForkedToken;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.TokenFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Offer;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Trace;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.TraceFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanBinaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanUnaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerCalculationExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerComparisonExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.ValuesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.Variable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.VariablesFactory;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class FactoriesProvider {

    final static Schema runtimeActivityDiagramSchema = SchemaLoader.load(SchemaFactoryProvider.getSchemaFactory(),

            // Activity
            Activity.class,
            RuntimeActivity.class,

            // Edges
            ActivityEdge.class, ControlFlow.class,
            RuntimeActivityEdge.class, RuntimeControlFlow.class,

            // Nodes
            ActivityNode.class,
            ExecutableNode.class, Action.class, OpaqueAction.class,
            FinalNode.class, ControlNode.class, ActivityFinalNode.class, DecisionNode.class,
            ForkNode.class, InitialNode.class, MergeNode.class, JoinNode.class,

            RuntimeActivityNode.class,
            RuntimeExecutableNode.class, RuntimeAction.class, RuntimeOpaqueAction.class,
            RuntimeFinalNode.class, RuntimeControlNode.class, RuntimeActivityFinalNode.class, RuntimeDecisionNode.class,
            RuntimeForkNode.class, RuntimeInitialNode.class, RuntimeMergeNode.class, RuntimeJoinNode.class,

            // Values
            Value.class, BooleanValue.class, IntegerValue.class,
            InputValue.class,

            // Variables
            Variable.class, BooleanVariable.class, IntegerVariable.class,

            // Tokens
            Token.class, ForkedToken.class, ControlToken.class,

            // Expressions
            Expression.class,
            BooleanExpression.class, BooleanBinaryExpression.class, BooleanUnaryExpression.class,
            IntegerExpression.class, IntegerCalculationExpression.class, IntegerComparisonExpression.class,

            // Extras
            Offer.class, Trace.class,

            // Primitives
            OperatorsPrimitives.class);

    private static IDataManager dataManager = new BasicDataManager();

    // ===================
    // static factories
    private static TokenFactory tokenFactory = dataManager.factory(TokenFactory.class, runtimeActivityDiagramSchema);
    private static TraceFactory traceFactory = dataManager.factory(TraceFactory.class, runtimeActivityDiagramSchema);
    private static ValuesFactory valuesFactory = dataManager.factory(ValuesFactory.class, runtimeActivityDiagramSchema);
    private static VariablesFactory variablesFactory = dataManager.factory(VariablesFactory.class, runtimeActivityDiagramSchema);

    public static TokenFactory getTokenFactory() {
        return tokenFactory;
    }

    public static ValuesFactory getValuesFactory() {
        return valuesFactory;
    }

    public static TraceFactory getTraceFactory() {
        return traceFactory;
    }

    public static VariablesFactory getVariablesFactory() {
        return variablesFactory;
    }

    // ===================
    // runtime factories
    private static RuntimeActivityFactory runtimeActivityFactory =
                dataManager.factory(RuntimeActivityFactory.class, runtimeActivityDiagramSchema);

    private static RuntimeNodesFactory runtimeNodesFactory =
                dataManager.factory(RuntimeNodesFactory.class, runtimeActivityDiagramSchema);

    private static RuntimeEdgesFactory runtimeEdgesFactory =
                dataManager.factory(RuntimeEdgesFactory.class, runtimeActivityDiagramSchema);

    public static RuntimeNodesFactory getRuntimeNodesFactory() {
        return runtimeNodesFactory;
    }

    public static RuntimeEdgesFactory getRuntimeEdgesFactory() {
        return runtimeEdgesFactory;
    }

    public static RuntimeActivityFactory getRuntimeActivityFactory() {
        return runtimeActivityFactory;
    }
}
