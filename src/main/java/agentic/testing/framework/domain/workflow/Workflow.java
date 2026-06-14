package agentic.testing.framework.domain.workflow;

import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.robot.Capability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DDD Aggregate Root.
 * Workflow controls ordered Tasks and becomes the base unit for compatibility validation, testing and deployment.
 */
public class Workflow implements Identifiable {
    private final String workflowId;
    private final String name;
    private final String description;
    private final List<Task> tasks;
    private WorkflowStatus status;

    public Workflow(String workflowId, String name, String description, List<Task> tasks) {
        ValidationUtils.requireText(workflowId, "workflowId");
        this.workflowId = workflowId.trim();
        this.name = name == null ? "" : name.trim();
        this.description = description == null ? "" : description.trim();
        this.tasks = new ArrayList<>(tasks == null ? Collections.emptyList() : tasks);
        this.status = WorkflowStatus.DRAFT;
    }

    public Workflow createWorkflow() {
        checkWorkflow();
        this.status = WorkflowStatus.READY;
        return this;
    }

    public boolean checkWorkflow() {
        ValidationUtils.requireText(name, "workflow.name");
        ValidationUtils.requireNotEmpty(tasks, "workflow.tasks");

        Set<String> taskIds = new LinkedHashSet<>();
        for (Task task : tasks) {
            ValidationUtils.requireNotNull(task, "workflow.task");
            task.checkRequirement();
            if (!taskIds.add(task.getTaskId())) {
                throw new DomainValidationException("Workflow contains duplicated taskId: " + task.getTaskId());
            }
        }
        return true;
    }

    public List<Capability> getRequiredCapabilities() {
        Set<Capability> uniqueCapabilities = new LinkedHashSet<>();
        for (Task task : tasks) {
            uniqueCapabilities.addAll(task.getRequiredCapabilities());
        }
        return new ArrayList<>(uniqueCapabilities);
    }

    public void changeStatus(WorkflowStatus status) {
        ValidationUtils.requireNotNull(status, "status");
        this.status = status;
    }

    @Override
    public String getId() {
        return workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "workflowId='" + workflowId + '\'' +
                ", name='" + name + '\'' +
                ", tasks=" + tasks.size() +
                ", status=" + status +
                '}';
    }
}
