package agentic.testing.framework.domain.robot;

import agentic.testing.framework.domain.common.ValidationUtils;

public class Actuator {
    private final String actuatorId;
    private final String name;
    private final String actuatorType;
    private final String status;

    public Actuator(String actuatorId, String name, String actuatorType, String status) {
        ValidationUtils.requireText(actuatorId, "actuatorId");
        ValidationUtils.requireText(name, "name");
        ValidationUtils.requireText(actuatorType, "actuatorType");
        this.actuatorId = actuatorId.trim();
        this.name = name.trim();
        this.actuatorType = actuatorType.trim();
        this.status = status == null || status.trim().isEmpty() ? "active" : status.trim();
    }

    public String getActuatorId() {
        return actuatorId;
    }

    public String getName() {
        return name;
    }

    public String getActuatorType() {
        return actuatorType;
    }

    public String getStatus() {
        return status;
    }

    public String getActuatorInfo() {
        return name + " [" + actuatorType + ", " + status + "]";
    }

    @Override
    public String toString() {
        return getActuatorInfo();
    }
}
