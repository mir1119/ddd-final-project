package agentic.testing.framework.domain.testing;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

/**
 * Defines the test environment and parameters used for a Workflow test.
 */
public class TestScenario implements Identifiable {
    private final String scenarioId;
    private final String name;
    private final String environment;
    private final String parameters;

    public TestScenario(String scenarioId, String name, String environment, String parameters) {
        ValidationUtils.requireText(scenarioId, "scenarioId");
        this.scenarioId = scenarioId.trim();
        this.name = name == null ? "" : name.trim();
        this.environment = environment == null ? "" : environment.trim();
        this.parameters = parameters == null ? "" : parameters.trim();
    }

    public TestScenario setScenario() {
        ValidationUtils.requireText(name, "scenario.name");
        ValidationUtils.requireText(environment, "scenario.environment");
        return this;
    }

    @Override
    public String getId() {
        return scenarioId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getName() {
        return name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "TestScenario{" +
                "scenarioId='" + scenarioId + '\'' +
                ", name='" + name + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
