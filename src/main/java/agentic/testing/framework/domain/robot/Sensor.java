package agentic.testing.framework.domain.robot;

import agentic.testing.framework.domain.common.ValidationUtils;

public class Sensor {
    private final String sensorId;
    private final String name;
    private final String sensorType;
    private final String status;

    public Sensor(String sensorId, String name, String sensorType, String status) {
        ValidationUtils.requireText(sensorId, "sensorId");
        ValidationUtils.requireText(name, "name");
        ValidationUtils.requireText(sensorType, "sensorType");
        this.sensorId = sensorId.trim();
        this.name = name.trim();
        this.sensorType = sensorType.trim();
        this.status = status == null || status.trim().isEmpty() ? "active" : status.trim();
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getName() {
        return name;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getStatus() {
        return status;
    }

    public String getSensorInfo() {
        return name + " [" + sensorType + ", " + status + "]";
    }

    @Override
    public String toString() {
        return getSensorInfo();
    }
}
