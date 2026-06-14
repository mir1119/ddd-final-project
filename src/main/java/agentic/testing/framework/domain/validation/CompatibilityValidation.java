package agentic.testing.framework.domain.validation;

import agentic.testing.framework.domain.agent.Agent;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.robot.Capability;
import agentic.testing.framework.domain.workflow.Workflow;
import agentic.testing.framework.domain.workflow.WorkflowStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DDD Domain Service style object.
 * It checks cross-aggregate business rules between Agent and Workflow.
 */
public class CompatibilityValidation implements Identifiable {
    private final String validationId;
    private final Agent agent;
    private final Workflow workflow;
    private boolean compatible;
    private final List<Capability> missingCapabilities;
    private LocalDateTime checkedAt;

    public CompatibilityValidation(String validationId, Agent agent, Workflow workflow) {
        ValidationUtils.requireText(validationId, "validationId");
        ValidationUtils.requireNotNull(agent, "agent");
        ValidationUtils.requireNotNull(workflow, "workflow");
        this.validationId = validationId.trim();
        this.agent = agent;
        this.workflow = workflow;
        this.compatible = false;
        this.missingCapabilities = new ArrayList<>();
    }

    public CompatibilityValidation validate() {
        workflow.checkWorkflow();
        missingCapabilities.clear();

        for (Capability required : workflow.getRequiredCapabilities()) {
            boolean found = false;
            for (Capability owned : agent.getCapabilities()) {
                if (owned.match(required)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingCapabilities.add(required);
            }
        }

        compatible = missingCapabilities.isEmpty();
        checkedAt = LocalDateTime.now();
        if (compatible) {
            workflow.changeStatus(WorkflowStatus.VALIDATED);
        }
        return this;
    }

    @Override
    public String getId() {
        return validationId;
    }

    public String getValidationId() {
        return validationId;
    }

    public Agent getAgent() {
        return agent;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public List<Capability> getMissingCapabilities() {
        return Collections.unmodifiableList(missingCapabilities);
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public String getMessage() {
        if (compatible) {
            return "Agent is compatible with this workflow.";
        }
        return "Missing capabilities: " + missingCapabilities;
    }

    @Override
    public String toString() {
        return "CompatibilityValidation{" +
                "validationId='" + validationId + '\'' +
                ", compatible=" + compatible +
                ", missingCapabilities=" + missingCapabilities +
                ", checkedAt=" + checkedAt +
                '}';
    }
}
