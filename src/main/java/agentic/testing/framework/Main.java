package agentic.testing.framework;

import agentic.testing.framework.application.AgenticTestingApplicationService;
import agentic.testing.framework.domain.agent.Agent;
import agentic.testing.framework.domain.deployment.Deployment;
import agentic.testing.framework.domain.evaluation.EnergyEvaluation;
import agentic.testing.framework.domain.evaluation.EvaluationReport;
import agentic.testing.framework.domain.evaluation.PerformanceEvaluation;
import agentic.testing.framework.domain.robot.Actuator;
import agentic.testing.framework.domain.robot.Capability;
import agentic.testing.framework.domain.robot.Robot;
import agentic.testing.framework.domain.robot.Sensor;
import agentic.testing.framework.domain.testing.TestRun;
import agentic.testing.framework.domain.testing.TestScenario;
import agentic.testing.framework.domain.validation.CompatibilityValidation;
import agentic.testing.framework.domain.workflow.Task;
import agentic.testing.framework.domain.workflow.Workflow;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AgenticTestingApplicationService app = new AgenticTestingApplicationService();

        Capability move = new Capability("cap-001", "move", "Robot can move in the environment.");
        Capability camera = new Capability("cap-002", "camera", "Robot can capture visual data.");
        Capability arm = new Capability("cap-003", "arm_control", "Robot can control robotic arm.");

        // UC-01 Register Robot
        Robot robot = new Robot(
                "robot-001",
                "WarehouseBot-A",
                "mobile robot",
                "CPU: Edge-AI board, RAM: 8GB, Battery: 5000mAh",
                Arrays.asList(
                        new Sensor("sensor-001", "Front Camera", "camera", "active"),
                        new Sensor("sensor-002", "Distance Sensor", "lidar", "active")
                ),
                Arrays.asList(
                        new Actuator("actuator-001", "Wheel Motor", "motor", "active"),
                        new Actuator("actuator-002", "Robot Arm", "arm", "active")
                ),
                Arrays.asList(move, camera, arm)
        );
        app.registerRobot(robot);

        // UC-02 Create Agent
        Agent agent = app.createAgent("agent-001", "robot-001");

        // UC-03 Define Task
        Task inspectShelf = new Task(
                "task-001",
                "Inspect shelf and capture product image",
                Arrays.asList(move, camera),
                "Indoor warehouse only",
                "Product image is captured successfully"
        );
        Task pickObject = new Task(
                "task-002",
                "Pick target object from shelf",
                Arrays.asList(arm, camera),
                "Object weight must be under 2kg",
                "Object is picked successfully"
        );
        app.defineTask(inspectShelf);
        app.defineTask(pickObject);

        // UC-04 Create Workflow
        Workflow workflow = new Workflow(
                "workflow-001",
                "Warehouse inspection and pick workflow",
                "A workflow that inspects a shelf and picks a selected object.",
                Arrays.asList(inspectShelf, pickObject)
        );
        app.createWorkflow(workflow);

        // UC-05 Validate Workflow Compatibility
        CompatibilityValidation validation = app.validateWorkflowCompatibility(
                "validation-001",
                "agent-001",
                "workflow-001"
        );

        // UC-06 Run Workflow Test
        TestScenario scenario = new TestScenario(
                "scenario-001",
                "Warehouse simulation scenario",
                "Simulated warehouse aisle",
                "light=normal; obstacle=low; battery=full"
        );
        app.setScenario(scenario);
        TestRun testRun = app.runWorkflowTest("test-run-001", "agent-001", "workflow-001", "scenario-001");

        // UC-07 Deploy Workflow to Agent
        Deployment deployment = app.deployWorkflow("deployment-001", "agent-001", "workflow-001");

        // UC-08 Evaluate Performance
        PerformanceEvaluation performanceEvaluation = app.evaluatePerformance("performance-evaluation-001");

        // UC-09 Evaluate Energy Consumption
        EnergyEvaluation energyEvaluation = app.evaluateEnergy("energy-evaluation-001");
        EvaluationReport report = app.generateEvaluationReport(
                "report-001",
                performanceEvaluation,
                energyEvaluation
        );

        printDivider("Agentic Testing Framework Demo");
        System.out.println("Registered Robot: " + robot);
        System.out.println("Created Agent: " + agent);
        System.out.println("Validation Result: " + validation.getMessage());
        printDivider("Test Report");
        System.out.println(testRun.generateTestReport());
        printDivider("Deployment");
        System.out.println("Deployment Status: " + deployment.checkDeploymentStatus());
        System.out.println("Agent Status: " + agent.getStatus());
        printDivider("Evaluation Report");
        System.out.println(report.getSummary());
    }

    private static void printDivider(String title) {
        System.out.println();
        System.out.println("==== " + title + " ====");
    }
}
