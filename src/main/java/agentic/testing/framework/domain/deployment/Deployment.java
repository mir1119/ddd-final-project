package agentic.testing.framework.domain.deployment;

import agentic.testing.framework.domain.agent.Agent;
import agentic.testing.framework.domain.agent.AgentState;
import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.evaluation.ExecutionRecord;
import agentic.testing.framework.domain.evaluation.SourceType;
import agentic.testing.framework.domain.validation.CompatibilityValidation;
import agentic.testing.framework.domain.workflow.Workflow;
import agentic.testing.framework.domain.workflow.WorkflowStatus;

import java.time.LocalDateTime;

/**
 * Deploys a validated and tested Workflow to an Agent.
 */
public class Deployment implements Identifiable {
    private final String deploymentId;
    private final Agent agent;
    private final Workflow workflow;
    private DeploymentStatus status;
    private LocalDateTime deployedAt;
    private ExecutionRecord executionRecord;

    public Deployment(String deploymentId, Agent agent, Workflow workflow) {
        ValidationUtils.requireText(deploymentId, "deploymentId");
        ValidationUtils.requireNotNull(agent, "agent");
        ValidationUtils.requireNotNull(workflow, "workflow");
        this.deploymentId = deploymentId.trim();
        this.agent = agent;
        this.workflow = workflow;
        this.status = DeploymentStatus.CREATED;
    }

    public Deployment deployWorkflow(CompatibilityValidation validation, boolean hasSuccessfulTestRun) {
        ValidationUtils.requireNotNull(validation, "validation");
        if (!validation.isCompatible()) {
            failDeployment("Workflow is not compatible with this Agent. " + validation.getMessage());
            throw new DomainValidationException("Cannot deploy incompatible workflow. " + validation.getMessage());
        }
        if (!hasSuccessfulTestRun) {
            failDeployment("Workflow must pass testing before deployment.");
            throw new DomainValidationException("Workflow must pass testing before deployment.");
        }

        LocalDateTime start = LocalDateTime.now();
        deployedAt = start.plusSeconds(2);
        status = DeploymentStatus.DEPLOYED;
        agent.changeState(AgentState.DEPLOYED);
        workflow.changeStatus(WorkflowStatus.DEPLOYED);
        executionRecord = ExecutionRecord.recordExecution(
                "record-" + deploymentId,
                SourceType.DEPLOYMENT,
                start,
                deployedAt,
                true,
                8.0,
                3.5,
                ""
        );
        return this;
    }

    public DeploymentStatus checkDeploymentStatus() {
        return status;
    }

    private void failDeployment(String errorMessage) {
        LocalDateTime now = LocalDateTime.now();
        status = DeploymentStatus.FAILED;
        deployedAt = now;
        agent.changeState(AgentState.ERROR);
        executionRecord = ExecutionRecord.recordExecution(
                "record-" + deploymentId,
                SourceType.DEPLOYMENT,
                now,
                now,
                false,
                0,
                0,
                errorMessage
        );
    }

    @Override
    public String getId() {
        return deploymentId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public Agent getAgent() {
        return agent;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public DeploymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getDeployedAt() {
        return deployedAt;
    }

    public ExecutionRecord getExecutionRecord() {
        return executionRecord;
    }

    @Override
    public String toString() {
        return "Deployment{" +
                "deploymentId='" + deploymentId + '\'' +
                ", agent=" + agent.getAgentId() +
                ", workflow=" + workflow.getName() +
                ", status=" + status +
                '}';
    }
}
