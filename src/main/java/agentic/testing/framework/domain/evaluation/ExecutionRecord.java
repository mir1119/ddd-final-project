package agentic.testing.framework.domain.evaluation;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Records each test or deployment execution result.
 */
public class ExecutionRecord implements Identifiable {
    private final String recordId;
    private final SourceType sourceType;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final boolean success;
    private final double executionTime;
    private final double resourceUsage;
    private final double energyConsumption;
    private final String errorMessage;

    private ExecutionRecord(String recordId,
                            SourceType sourceType,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            boolean success,
                            double executionTime,
                            double resourceUsage,
                            double energyConsumption,
                            String errorMessage) {
        this.recordId = recordId;
        this.sourceType = sourceType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.success = success;
        this.executionTime = executionTime;
        this.resourceUsage = resourceUsage;
        this.energyConsumption = energyConsumption;
        this.errorMessage = errorMessage == null ? "" : errorMessage;
    }

    public static ExecutionRecord recordExecution(String recordId,
                                                  SourceType sourceType,
                                                  LocalDateTime startTime,
                                                  LocalDateTime endTime,
                                                  boolean success,
                                                  double resourceUsage,
                                                  double energyConsumption,
                                                  String errorMessage) {
        ValidationUtils.requireText(recordId, "recordId");
        ValidationUtils.requireNotNull(sourceType, "sourceType");
        ValidationUtils.requireNotNull(startTime, "startTime");
        ValidationUtils.requireNotNull(endTime, "endTime");
        ValidationUtils.requireNonNegative(resourceUsage, "resourceUsage");
        ValidationUtils.requireNonNegative(energyConsumption, "energyConsumption");
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("endTime must not be before startTime.");
        }
        double executionTime = Duration.between(startTime, endTime).toMillis() / 1000.0;
        return new ExecutionRecord(
                recordId.trim(),
                sourceType,
                startTime,
                endTime,
                success,
                executionTime,
                resourceUsage,
                energyConsumption,
                errorMessage
        );
    }

    @Override
    public String getId() {
        return recordId;
    }

    public String getRecordId() {
        return recordId;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public double getResourceUsage() {
        return resourceUsage;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ExecutionRecord{" +
                "recordId='" + recordId + '\'' +
                ", sourceType=" + sourceType +
                ", success=" + success +
                ", executionTime=" + executionTime +
                ", resourceUsage=" + resourceUsage +
                ", energyConsumption=" + energyConsumption +
                (errorMessage.isEmpty() ? "" : ", errorMessage='" + errorMessage + '\'') +
                '}';
    }
}
