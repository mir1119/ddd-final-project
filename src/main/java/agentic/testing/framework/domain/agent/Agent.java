package agentic.testing.framework.domain.agent;

import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;
import agentic.testing.framework.domain.robot.Capability;
import agentic.testing.framework.domain.robot.Robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DDD Entity.
 * Agent is an abstraction generated from a registered Robot.
 */
public class Agent implements Identifiable {
    private final String agentId;
    private final Robot robot;
    private AgentState state;
    private final List<Capability> capabilities;

    private Agent(String agentId, Robot robot, AgentState state, List<Capability> capabilities) {
        this.agentId = agentId;
        this.robot = robot;
        this.state = state;
        this.capabilities = new ArrayList<>(capabilities);
    }

    public static Agent createFromRobot(String agentId, Robot robot) {
        ValidationUtils.requireText(agentId, "agentId");
        ValidationUtils.requireNotNull(robot, "robot");
        if (!robot.isRegistered()) {
            throw new DomainValidationException("Robot must be registered before creating an Agent.");
        }
        robot.checkProfile();
        return new Agent(agentId.trim(), robot, AgentState.IDLE, robot.getCapabilities());
    }

    public AgentState getStatus() {
        return state;
    }

    public void changeState(AgentState newState) {
        ValidationUtils.requireNotNull(newState, "newState");
        this.state = newState;
    }

    @Override
    public String getId() {
        return agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public Robot getRobot() {
        return robot;
    }

    public AgentState getState() {
        return state;
    }

    public List<Capability> getCapabilities() {
        return Collections.unmodifiableList(capabilities);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId='" + agentId + '\'' +
                ", robot=" + robot.getName() +
                ", state=" + state +
                ", capabilities=" + capabilities +
                '}';
    }
}
