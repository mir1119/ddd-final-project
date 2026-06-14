package agentic.testing.framework.domain.evaluation;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.time.LocalDateTime;

/**
 * Summarizes performance and energy evaluation results.
 */
public class EvaluationReport implements Identifiable {
    private final String reportId;
    private final PerformanceEvaluation performanceEvaluation;
    private final EnergyEvaluation energyEvaluation;
    private LocalDateTime generatedAt;
    private String summary;

    public EvaluationReport(String reportId,
                            PerformanceEvaluation performanceEvaluation,
                            EnergyEvaluation energyEvaluation) {
        ValidationUtils.requireText(reportId, "reportId");
        ValidationUtils.requireNotNull(performanceEvaluation, "performanceEvaluation");
        ValidationUtils.requireNotNull(energyEvaluation, "energyEvaluation");
        this.reportId = reportId.trim();
        this.performanceEvaluation = performanceEvaluation;
        this.energyEvaluation = energyEvaluation;
    }

    public EvaluationReport generateEvaluationReport() {
        generatedAt = LocalDateTime.now();
        summary = "Evaluation Report\n" +
                "- generatedAt: " + generatedAt + "\n" +
                "- averageExecutionTime: " + performanceEvaluation.getAverageExecutionTime() + " seconds\n" +
                "- successRate: " + performanceEvaluation.getSuccessRate() + "%\n" +
                "- resourceUsageSummary: " + performanceEvaluation.getResourceUsageSummary() + "\n" +
                "- totalEnergyConsumption: " + energyEvaluation.getTotalEnergyConsumption() + "\n" +
                "- averageEnergyConsumption: " + energyEvaluation.getAverageEnergyConsumption() + "\n" +
                "- energyEfficiency: " + energyEvaluation.getEnergyEfficiency() + " successful runs per energy unit";
        return this;
    }

    @Override
    public String getId() {
        return reportId;
    }

    public String getReportId() {
        return reportId;
    }

    public PerformanceEvaluation getPerformanceEvaluation() {
        return performanceEvaluation;
    }

    public EnergyEvaluation getEnergyEvaluation() {
        return energyEvaluation;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString() {
        return summary == null ? "EvaluationReport{" + reportId + "}" : summary;
    }
}
