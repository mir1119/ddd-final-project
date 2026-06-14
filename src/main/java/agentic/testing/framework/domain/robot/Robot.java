package agentic.testing.framework.domain.robot;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DDD Entity.
 * Robot owns Sensor, Actuator and Capability information used to create an Agent.
 */
public class Robot implements Identifiable {
    private final String robotId;
    private final String name;
    private final String type;
    private final String hardwareSpec;
    private final List<Sensor> sensors;
    private final List<Actuator> actuators;
    private final List<Capability> capabilities;
    private boolean registered;

    public Robot(String robotId,
                 String name,
                 String type,
                 String hardwareSpec,
                 List<Sensor> sensors,
                 List<Actuator> actuators,
                 List<Capability> capabilities) {
        ValidationUtils.requireText(robotId, "robotId");
        this.robotId = robotId.trim();
        this.name = name == null ? "" : name.trim();
        this.type = type == null ? "" : type.trim();
        this.hardwareSpec = hardwareSpec == null ? "" : hardwareSpec.trim();
        this.sensors = new ArrayList<>(sensors == null ? Collections.emptyList() : sensors);
        this.actuators = new ArrayList<>(actuators == null ? Collections.emptyList() : actuators);
        this.capabilities = new ArrayList<>(capabilities == null ? Collections.emptyList() : capabilities);
        this.registered = false;
    }

    public Robot register() {
        checkProfile();
        registered = true;
        return this;
    }

    public boolean checkProfile() {
        ValidationUtils.requireText(name, "robot.name");
        ValidationUtils.requireText(type, "robot.type");
        ValidationUtils.requireText(hardwareSpec, "robot.hardwareSpec");
        ValidationUtils.requireNotEmpty(sensors, "robot.sensors");
        ValidationUtils.requireNotEmpty(actuators, "robot.actuators");
        ValidationUtils.requireNotEmpty(capabilities, "robot.capabilities");
        return true;
    }

    @Override
    public String getId() {
        return robotId;
    }

    public String getRobotId() {
        return robotId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHardwareSpec() {
        return hardwareSpec;
    }

    public List<Sensor> getSensors() {
        return Collections.unmodifiableList(sensors);
    }

    public List<Actuator> getActuators() {
        return Collections.unmodifiableList(actuators);
    }

    public List<Capability> getCapabilities() {
        return Collections.unmodifiableList(capabilities);
    }

    public boolean isRegistered() {
        return registered;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "robotId='" + robotId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", capabilities=" + capabilities +
                '}';
    }
}
