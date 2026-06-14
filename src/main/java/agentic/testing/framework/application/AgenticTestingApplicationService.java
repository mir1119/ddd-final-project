package agentic.testing.framework.application;

import agentic.testing.framework.domain.agent.Agent;
import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.NotFoundException;
import agentic.testing.framework.domain.deployment.Deployment;
import agentic.testing.framework.domain.evaluation.EnergyEvaluation;
import agentic.testing.framework.domain.evaluation.EvaluationReport;
import agentic.testing.framework.domain.evaluation.ExecutionRecord;
import agentic.testing.framework.domain.evaluation.PerformanceEvaluation;
import agentic.testing.framework.domain.robot.Robot;
import agentic.testing.framework.domain.testing.RunStatus;
import agentic.testing.framework.domain.testing.TestRun;
import agentic.testing.framework.domain.testing.TestScenario;
import agentic.testing.framework.domain.validation.CompatibilityValidation;
import agentic.testing.framework.domain.workflow.Task;
import agentic.testing.framework.domain.workflow.Workflow;
import agentic.testing.framework.infrastructure.repository.GenericRepository;
import agentic.testing.framework.infrastructure.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Application Service: orchestrates the nine use cases from the requirement document.
 * Domain rules still remain inside domain objects.
 */
public class AgenticTestingApplicationService {
    private final GenericRepository<Robot> robotRepository = new InMemoryRepository<>();
    private final GenericRepository<Agent> agentRepository = new InMemoryRepository<>();
    private final GenericRepository<Task> taskRepository = new InMemoryRepository<>();
    private final GenericRepository<Workflow> workflowRepository = new InMemoryRepository<>();
    private final GenericRepository<TestScenario> scenarioRepository = new InMemoryRepository<>();
    private final GenericRepository<CompatibilityValidation> validationRepository = new InMemoryRepository<>();
    private final GenericRepository<TestRun> testRunRepository = new InMemoryRepository<>();
    private final GenericRepository<Deployment> deploymentRepository = new InMemoryRepository<>();
    private final GenericRepository<ExecutionRecord> executionRecordRepository = new InMemoryRepository<>();

    public Robot registerRobot(Robot robot) {
        if (robotRepository.existsById(robot.getRobotId())) {
            throw new DomainValidationException("Robot already exists: " + robot.getRobotId());
        }
        robot.register();
        return robotRepository.save(robot);
    }

    public Agent createAgent(String agentId, String robotId) {
        if (agentRepository.existsById(agentId)) {
            throw new DomainValidationException("Agent already exists: " + agentId);
        }
        Robot robot = findRobot(robotId);
        Agent agent = Agent.createFromRobot(agentId, robot);
        return agentRepository.save(agent);
    }

    public Task defineTask(Task task) {
        if (taskRepository.existsById(task.getTaskId())) {
            throw new DomainValidationException("Task already exists: " + task.getTaskId());
        }
        task.defineTask();
        return taskRepository.save(task);
    }

    public Workflow createWorkflow(Workflow workflow) {
        if (workflowRepository.existsById(workflow.getWorkflowId())) {
            throw new DomainValidationException("Workflow already exists: " + workflow.getWorkflowId());
        }
        workflow.createWorkflow();
        return workflowRepository.save(workflow);
    }

    public CompatibilityValidation validateWorkflowCompatibility(String validationId, String agentId, String workflowId) {
        Agent agent = findAgent(agentId);
        Workflow workflow = findWorkflow(workflowId);
        CompatibilityValidation validation = new CompatibilityValidation(validationId, agent, workflow).validate();
        return validationRepository.save(validation);
    }

    public TestScenario setScenario(TestScenario scenario) {
        if (scenarioRepository.existsById(scenario.getScenarioId())) {
            throw new DomainValidationException("Scenario already exists: " + scenario.getScenarioId());
        }
        scenario.setScenario();
        return scenarioRepository.save(scenario);
    }

    public TestRun runWorkflowTest(String testRunId, String agentId, String workflowId, String scenarioId) {
        Agent agent = findAgent(agentId);
        Workflow workflow = findWorkflow(workflowId);
        TestScenario scenario = findScenario(scenarioId);

        CompatibilityValidation validation = new CompatibilityValidation("validation-for-" + testRunId, agent, workflow).validate();
        validationRepository.save(validation);

        TestRun testRun = new TestRun(testRunId, agent, workflow, scenario).runTest(validation);
        testRunRepository.save(testRun);
        executionRecordRepository.save(testRun.getExecutionRecord());
        return testRun;
    }

    public Deployment deployWorkflow(String deploymentId, String agentId, String workflowId) {
        Agent agent = findAgent(agentId);
        Workflow workflow = findWorkflow(workflowId);
        CompatibilityValidation validation = new CompatibilityValidation("validation-for-" + deploymentId, agent, workflow).validate();
        validationRepository.save(validation);

        boolean hasSuccessfulTestRun = testRunRepository.findAll().stream()
                .anyMatch(testRun ->
                        testRun.getAgent().getAgentId().equals(agentId)
                                && testRun.getWorkflow().getWorkflowId().equals(workflowId)
                                && testRun.getStatus() == RunStatus.SUCCESS);

        Deployment deployment = new Deployment(deploymentId, agent, workflow)
                .deployWorkflow(validation, hasSuccessfulTestRun);
        deploymentRepository.save(deployment);
        executionRecordRepository.save(deployment.getExecutionRecord());
        return deployment;
    }

    public PerformanceEvaluation evaluatePerformance(String performanceEvaluationId) {
        PerformanceEvaluation evaluation = new PerformanceEvaluation(performanceEvaluationId, executionRecordRepository.findAll())
                .evaluatePerformance();
        return evaluation;
    }

    public EnergyEvaluation evaluateEnergy(String energyEvaluationId) {
        EnergyEvaluation evaluation = new EnergyEvaluation(energyEvaluationId, executionRecordRepository.findAll())
                .evaluateEnergy();
        return evaluation;
    }

    public EvaluationReport generateEvaluationReport(String reportId,
                                                     PerformanceEvaluation performanceEvaluation,
                                                     EnergyEvaluation energyEvaluation) {
        return new EvaluationReport(reportId, performanceEvaluation, energyEvaluation).generateEvaluationReport();
    }

    public List<ExecutionRecord> getExecutionRecords() {
        return new ArrayList<>(executionRecordRepository.findAll());
    }

    public List<Robot> getRobots() {
        return robotRepository.findAll();
    }

    public List<Agent> getAgents() {
        return agentRepository.findAll();
    }

    public List<Workflow> getWorkflows() {
        return workflowRepository.findAll();
    }

    private Robot findRobot(String robotId) {
        return robotRepository.findById(robotId)
                .orElseThrow(() -> new NotFoundException("Robot not found: " + robotId));
    }

    private Agent findAgent(String agentId) {
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Agent not found: " + agentId));
    }

    private Workflow findWorkflow(String workflowId) {
        return workflowRepository.findById(workflowId)
                .orElseThrow(() -> new NotFoundException("Workflow not found: " + workflowId));
    }

    private TestScenario findScenario(String scenarioId) {
        return scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new NotFoundException("Scenario not found: " + scenarioId));
    }
}
