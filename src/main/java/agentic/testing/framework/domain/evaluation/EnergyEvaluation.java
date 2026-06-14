package agentic.testing.framework.domain.evaluation;

import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Evaluates total energy, average energy and a simple energy efficiency score.
 */
public class EnergyEvaluation implements Identifiable {
    private final String energyEvaluationId;
    private final List<ExecutionRecord> records;
    private double totalEnergyConsumption;
    private double averageEnergyConsumption;
    private double energyEfficiency;

    public EnergyEvaluation(String energyEvaluationId, List<ExecutionRecord> records) {
        ValidationUtils.requireText(energyEvaluationId, "energyEvaluationId");
        this.energyEvaluationId = energyEvaluationId.trim();
        this.records = new ArrayList<>(records == null ? Collections.emptyList() : records);
    }

    public EnergyEvaluation evaluateEnergy() {
        ValidationUtils.requireNotEmpty(records, "energy.records");
        totalEnergyConsumption = 0;
        int successCount = 0;

        for (ExecutionRecord record : records) {
            totalEnergyConsumption += record.getEnergyConsumption();
            if (record.isSuccess()) {
                successCount++;
            }
        }

        averageEnergyConsumption = totalEnergyConsumption / records.size();
        energyEfficiency = totalEnergyConsumption == 0 ? 0 : successCount / totalEnergyConsumption;
        return this;
    }

    @Override
    public String getId() {
        return energyEvaluationId;
    }

    public String getEnergyEvaluationId() {
        return energyEvaluationId;
    }

    public List<ExecutionRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public double getTotalEnergyConsumption() {
        return totalEnergyConsumption;
    }

    public double getAverageEnergyConsumption() {
        return averageEnergyConsumption;
    }

    public double getEnergyEfficiency() {
        return energyEfficiency;
    }

    @Override
    public String toString() {
        return "EnergyEvaluation{" +
                "totalEnergyConsumption=" + totalEnergyConsumption +
                ", averageEnergyConsumption=" + averageEnergyConsumption +
                ", energyEfficiency=" + energyEfficiency +
                '}';
    }
}
