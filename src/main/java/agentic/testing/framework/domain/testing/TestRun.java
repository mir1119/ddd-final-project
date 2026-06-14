package agentic.testing.framework.domain.testing;

import agentic.testing.framework.domain.agent.Agent;
import agentic.testing.framework.domain.agent.AgentState;
import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.evaluation.ExecutionRecord;
import agentic.testing.framework.domain.evaluation.SourceType;
import agentic.testing.framework.domain.validation.CompatibilityValidation;
import agentic.testing.framework.domain.workflow.Task;
import agentic.testing.framework.domain.workflow.Workflow;
import agentic.testing.framework.domain.workflow.WorkflowStatus;

import java.time.LocalDateTime;

/**
 * Executes a Workflow on an Agent under a TestScenario.
 * The execution is simulated because the first implementation phase does not connect to real robots.
 */
public class TestRun implements Identifiable {
    private final String testRunId;
    private final Agent agent;
    private final Workflow workflow;
    private final TestScenario scenario;
    private RunStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private ExecutionRecord executionRecord;

    public TestRun(String testRunId, Agent agent, Workflow workflow, TestScenario scenario) {
        ValidationUtils.requireText(testRunId, "testRunId");
        ValidationUtils.requireNotNull(agent, "agent");
        ValidationUtils.requireNotNull(workflow, "workflow");
        ValidationUtils.requireNotNull(scenario, "scenario");
        this.testRunId = testRunId.trim();
        this.agent = agent;
        this.workflow = workflow;
        this.scenario = scenario;
        this.status = RunStatus.CREATED;
    }

    public TestRun runTest(CompatibilityValidation validation) {
        ValidationUtils.requireNotNull(validation, "validation");
        if (!validation.isCompatible()) {
            failRun("Workflow is not compatible with this Agent. " + validation.getMessage());
            throw new DomainValidationException("Cannot run incompatible workflow. " + validation.getMessage());
        }

        try {
            workflow.checkWorkflow();
            scenario.setScenario();
            agent.changeState(AgentState.TESTING);
            status = RunStatus.RUNNING;
            startedAt = LocalDateTime.now();

            double resourceUsage = calculateResourceUsage();
            double energyConsumption = calculateEnergyConsumption(resourceUsage);
            endedAt = startedAt.plusSeconds(Math.max(1, workflow.getTasks().size() * 5L));
            executionRecord = ExecutionRecord.recordExecution(
                    "record-" + testRunId,
                    SourceType.TEST_RUN,
                    startedAt,
                    endedAt,
                    true,
                    resourceUsage,
                    energyConsumption,
                    ""
            );

            status = RunStatus.SUCCESS;
            workflow.changeStatus(WorkflowStatus.TESTED);
            agent.changeState(AgentState.IDLE);
            return this;
        } catch (RuntimeException ex) {
            failRun(ex.getMessage());
            throw ex;
        }
    }

    public String generateTestReport() {
        if (executionRecord == null) {
            return "TestRun " + testRunId + " has not produced an execution record yet.";
        }
        return "TestRun Report\n" +
                "- testRunId: " + testRunId + "\n" +
                "- agent: " + agent.getAgentId() + "\n" +
                "- workflow: " + workflow.getName() + "\n" +
                "- scenario: " + scenario.getName() + "\n" +
                "- status: " + status + "\n" +
                "- executionTime: " + executionRecord.getExecutionTime() + " seconds\n" +
                "- resourceUsage: " + executionRecord.getResourceUsage() + "\n" +
                "- energyConsumption: " + executionRecord.getEnergyConsumption() + "\n" +
                (executionRecord.getErrorMessage().isEmpty() ? "" : "- error: " + executionRecord.getErrorMessage() + "\n");
    }

    private double calculateResourceUsage() {
        double total = 0;
        int order = 1;
        for (Task task : workflow.getTasks()) {
            total += 10.0 + task.getRequiredCapabilities().size() * 5.0 + order;
            order++;
        }
        return total;
    }

    private double calculateEnergyConsumption(double resourceUsage) {
        return resourceUsage * 0.35 + workflow.getTasks().size() * 1.2;
    }

    private void failRun(String errorMessage) {
        status = RunStatus.FAILED;
        LocalDateTime now = LocalDateTime.now();
        startedAt = startedAt == null ? now : startedAt;
        endedAt = now;
        agent.changeState(AgentState.ERROR);
        executionRecord = ExecutionRecord.recordExecution(
                "record-" + testRunId,
                SourceType.TEST_RUN,
                startedAt,
                endedAt,
                false,
                0,
                0,
                errorMessage
        );
    }

    @Override
    public String getId() {
        return testRunId;
    }

    public String getTestRunId() {
        return testRunId;
    }

    public Agent getAgent() {
        return agent;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public TestScenario getScenario() {
        return scenario;
    }

    public RunStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public ExecutionRecord getExecutionRecord() {
        return executionRecord;
    }

    @Override
    public String toString() {
        return "TestRun{" +
                "testRunId='" + testRunId + '\'' +
                ", agent=" + agent.getAgentId() +
                ", workflow=" + workflow.getName() +
                ", status=" + status +
                '}';
    }
}
