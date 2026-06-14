package agentic.testing.framework.domain.evaluation;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Evaluates execution time, success rate and resource usage from ExecutionRecord objects.
 */
public class PerformanceEvaluation implements Identifiable {
    private final String performanceEvaluationId;
    private final List<ExecutionRecord> records;
    private double averageExecutionTime;
    private double successRate;
    private double resourceUsageSummary;

    public PerformanceEvaluation(String performanceEvaluationId, List<ExecutionRecord> records) {
        ValidationUtils.requireText(performanceEvaluationId, "performanceEvaluationId");
        this.performanceEvaluationId = performanceEvaluationId.trim();
        this.records = new ArrayList<>(records == null ? Collections.emptyList() : records);
    }

    public PerformanceEvaluation evaluatePerformance() {
        ValidationUtils.requireNotEmpty(records, "performance.records");
        double totalTime = 0;
        double totalResourceUsage = 0;
        int successCount = 0;

        for (ExecutionRecord record : records) {
            totalTime += record.getExecutionTime();
            totalResourceUsage += record.getResourceUsage();
            if (record.isSuccess()) {
                successCount++;
            }
        }

        averageExecutionTime = totalTime / records.size();
        successRate = (successCount * 100.0) / records.size();
        resourceUsageSummary = totalResourceUsage;
        return this;
    }

    @Override
    public String getId() {
        return performanceEvaluationId;
    }

    public String getPerformanceEvaluationId() {
        return performanceEvaluationId;
    }

    public List<ExecutionRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public double getResourceUsageSummary() {
        return resourceUsageSummary;
    }

    @Override
    public String toString() {
        return "PerformanceEvaluation{" +
                "averageExecutionTime=" + averageExecutionTime +
                ", successRate=" + successRate +
                ", resourceUsageSummary=" + resourceUsageSummary +
                '}';
    }
}
