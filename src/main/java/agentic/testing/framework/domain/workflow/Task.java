package agentic.testing.framework.domain.workflow;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.robot.Capability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DDD Entity.
 * Task describes a reusable robot operation through goal, required capabilities, constraints and expected result.
 */
public class Task implements Identifiable {
    private final String taskId;
    private final String goal;
    private final List<Capability> requiredCapabilities;
    private final String constraints;
    private final String expectedResult;

    public Task(String taskId,
                String goal,
                List<Capability> requiredCapabilities,
                String constraints,
                String expectedResult) {
        ValidationUtils.requireText(taskId, "taskId");
        this.taskId = taskId.trim();
        this.goal = goal == null ? "" : goal.trim();
        this.requiredCapabilities = new ArrayList<>(requiredCapabilities == null ? Collections.emptyList() : requiredCapabilities);
        this.constraints = constraints == null ? "" : constraints.trim();
        this.expectedResult = expectedResult == null ? "" : expectedResult.trim();
    }

    public Task defineTask() {
        checkRequirement();
        return this;
    }

    public boolean checkRequirement() {
        ValidationUtils.requireText(goal, "task.goal");
        ValidationUtils.requireNotEmpty(requiredCapabilities, "task.requiredCapabilities");
        ValidationUtils.requireText(expectedResult, "task.expectedResult");
        return true;
    }

    @Override
    public String getId() {
        return taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getGoal() {
        return goal;
    }

    public List<Capability> getRequiredCapabilities() {
        return Collections.unmodifiableList(requiredCapabilities);
    }

    public String getConstraints() {
        return constraints;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", goal='" + goal + '\'' +
                ", requiredCapabilities=" + requiredCapabilities +
                '}';
    }
}
