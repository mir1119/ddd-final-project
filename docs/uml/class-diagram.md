---
config:
  layout: elk
---
classDiagram
    direction LR

    class User {
        -String userId
        -String name
        -String role
        +login()
        +logout()
    }

    class RoboticDeveloper {
        +registerRobot()
        +createAgent()
        +defineTask()
        +createWorkflow()
        +deployWorkflow()
    }

    class Tester {
        +runTest()
    }

    class Evaluator {
        +evaluatePerformance()
        +evaluateEnergy()
    }

    class Robot {
        -String robotId
        -String name
        -String type
        -String hardwareSpec
        -String status
        +register()
        +checkProfile()
    }

    class Sensor {
        -String sensorId
        -String type
        -String description
    }

    class Actuator {
        -String actuatorId
        -String type
        -String description
    }

    class Capability {
        -String capabilityId
        -String name
        -String description
    }

    class Agent {
        -String agentId
        -String name
        -String state
        +createFromRobot()
        +getStatus()
        +updateState()
    }

    class Task {
        -String taskId
        -String goal
        -String constraint
        -String expectedResult
        +defineTask()
        +checkRequirement()
    }

    class Workflow {
        -String workflowId
        -String name
        -String description
        -String status
        +createWorkflow()
        +addTask()
        +removeTask()
        +checkWorkflow()
    }

    class CompatibilityValidation {
        -String validationId
        -String result
        -String message
        +validate()
        +getValidationResult()
    }

    class TestScenario {
        -String scenarioId
        -String name
        -String parameter
        +prepare()
        +setParameter()
    }

    class TestRun {
        -String testRunId
        -DateTime startedAt
        -DateTime endedAt
        -String result
        +start()
        +stop()
        +recordResult()
    }

    class Deployment {
        -String deploymentId
        -String environment
        -String status
        -DateTime deployedAt
        +deploy()
        +rollback()
        +getDeploymentStatus()
    }

    class ExecutionRecord {
        -String recordId
        -DateTime startedAt
        -DateTime endedAt
        -String result
        -Float resourceUsage
        -Float energyConsumption
        +recordStart()
        +recordEnd()
        +getExecutionData()
    }

    class PerformanceEvaluation {
        -String evaluationId
        -Float executionTime
        -Float successRate
        -Float resourceUsage
        +computeThroughput()
        +computeLatency()
        +computeSuccessRate()
    }

    class EnergyEvaluation {
        -String evaluationId
        -Float energyConsumption
        -Float batteryUsage
        +computeEnergy()
        +computeBatteryUsage()
    }

    class EvaluationReport {
        -String reportId
        -DateTime createdAt
        -String summary
        +generate()
        +exportReport()
    }

    User <|-- RoboticDeveloper
    User <|-- Tester
    User <|-- Evaluator

    Robot "1" o-- "0..*" Sensor : has
    Robot "1" o-- "0..*" Actuator : has
    Robot "1" o-- "1..*" Capability : provides
    Robot "1" --> "0..1" Agent : creates

    Agent "1" --> "1" Robot : based on
    Agent "1" --> "1..*" Capability : owns

    Workflow "1" *-- "1..*" Task : contains
    Task "1" --> "1..*" Capability : requires

    CompatibilityValidation "1" --> "1" Agent : checks
    CompatibilityValidation "1" --> "1" Workflow : validates

    TestRun "1" --> "1" Agent : uses
    TestRun "1" --> "1" Workflow : tests
    TestRun "1" --> "1" TestScenario : runs in
    TestRun "1" --> "1" ExecutionRecord : creates

    Deployment "1" --> "1" Agent : targets
    Deployment "1" --> "1" Workflow : deploys
    Deployment "1" --> "1" ExecutionRecord : creates

    PerformanceEvaluation "1" --> "1" ExecutionRecord : analyzes
    EnergyEvaluation "1" --> "1" ExecutionRecord : analyzes

    EvaluationReport "1" --> "0..1" PerformanceEvaluation : includes
    EvaluationReport "1" --> "0..1" EnergyEvaluation : includes