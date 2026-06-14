# 02. Domain Analysis

## 1. Domain Overview

本專案的核心領域是 Agentic Testing Framework，也就是一個用於 robotic agent 的測試、部署與評估平台。

在這個領域中，不同 Robot 具有不同的 hardware specifications、sensors、actuators 與 capabilities。為了讓系統能以統一方式管理不同機器人，本系統會將 Robot 抽象成共同的 Agent model。Robotic Developer 可以定義 Task，並將多個 Task 組合成 Workflow。系統會在測試或部署前驗證 Agent 是否具備執行 Workflow 所需的能力。當 Workflow 被測試或部署後，系統會記錄 execution data，並提供 performance evaluation 與 energy evaluation。

因此，本系統的主要 domain concepts 包含：

* Robot
* Sensor
* Actuator
* Capability
* Agent
* Task
* Workflow
* Compatibility Validation
* Test Scenario
* Test Run
* Deployment
* Execution Record
* Performance Evaluation
* Energy Evaluation
* Evaluation Report

本專案使用 Domain-Driven Design（DDD）的方式進行分析，目標是讓系統的核心業務邏輯集中在 domain model 中，而不是分散在不同的 controller 或 service 裡。透過明確的領域語言、bounded context 與 domain objects，系統可以更容易維護與擴充。

## 2. Ubiquitous Language

| Term                     | Meaning                                                                 |
| ------------------------ | ----------------------------------------------------------------------- |
| Robot                    | 實際或模擬的機器人裝置，具有硬體規格、感測器、致動器與能力。                                          |
| Sensor                   | Robot 用來感知環境的元件，例如 camera、lidar、temperature sensor。                     |
| Actuator                 | Robot 用來執行動作的元件，例如 motor、robot arm、gripper。                             |
| Capability               | Robot 或 Agent 可執行的能力，例如 move、camera、arm_control。                        |
| Agent                    | 由 Robot 抽象而成的共同模型，用來執行 task、workflow、test 與 deployment。                 |
| Task                     | 一個可執行的任務單位，包含 goal、required capabilities、constraints 與 expected result。 |
| Workflow                 | 多個 Task 依照順序組合而成的完整任務流程。                                                |
| Compatibility Validation | 檢查 Agent 是否具備 Workflow 所需能力的驗證流程。                                       |
| Test Scenario            | Workflow 測試時使用的測試情境或參數設定。                                               |
| Test Run                 | 一次 Workflow 測試的執行紀錄。                                                    |
| Deployment               | 將已驗證或測試過的 Workflow 部署到指定 Agent 的流程。                                     |
| Execution Record         | 測試或部署後產生的執行紀錄，包含狀態、時間、資源使用與能源消耗。                                        |
| Performance Evaluation   | 根據 execution time、success rate、resource usage 評估效能。                     |
| Energy Evaluation        | 根據 energy consumption 評估能源使用情況。                                         |
| Evaluation Report        | 整合 performance evaluation 與 energy evaluation 的評估報告。                    |

## 3. Bounded Contexts

本系統依照主要業務責任切分為數個 Bounded Context。每個 context 負責一組明確的 domain concepts，避免所有邏輯混在同一個模組中。

## 3.1 Robot Management Context

### Responsibility

Robot Management Context 負責註冊與管理不同機器人的基本資料、硬體規格、感測器、致動器與能力。

### Main Concepts

* Robot
* Sensor
* Actuator
* Capability
* Hardware Specification

### Main Use Cases

* UC-01 Register Robot

### Description

在這個 context 中，Robotic Developer 可以建立 Robot profile。Robot profile 會記錄 Robot 的名稱、類型、硬體規格、sensors、actuators 與 capabilities。這些資料會成為後續建立 Agent 的基礎。

## 3.2 Agent Management Context

### Responsibility

Agent Management Context 負責根據已註冊的 Robot 建立 Agent，並管理 Agent 的狀態與能力資料。

### Main Concepts

* Agent
* Agent State
* Capability
* Robot

### Main Use Cases

* UC-02 Agent Abstraction

### Description

Agent 是系統中用來執行 task、workflow、test 與 deployment 的共同抽象模型。不同 Robot 可能有不同硬體結構，但系統會將它們轉換成統一的 Agent model，使後續流程能用一致方式操作。

## 3.3 Task and Workflow Context

### Responsibility

Task and Workflow Context 負責定義 Task，並將多個 Task 組合成完整的 Workflow。

### Main Concepts

* Task
* Workflow
* Goal
* Required Capability
* Constraint
* Expected Result
* Workflow Status

### Main Use Cases

* UC-03 Define Task
* UC-04 Create Workflow

### Description

Task 代表一個任務單位，例如移動、拍攝影像、抓取物件等。Workflow 則是由多個 Task 依照順序組成的完整任務流程。Workflow 是後續 compatibility validation、testing、deployment 與 evaluation 的基礎。

## 3.4 Compatibility Validation Context

### Responsibility

Compatibility Validation Context 負責檢查 Workflow 的任務需求是否與 Agent 的能力相容。

### Main Concepts

* Compatibility Validation
* Validation Result
* Agent
* Workflow
* Capability

### Main Use Cases

* UC-05 Validate Workflow Compatibility

### Description

在測試或部署 Workflow 之前，系統必須先確認 Agent 是否具備 Workflow 中所有 Task 所需的 capabilities。如果 Agent 缺少必要能力，系統應標示不相容，並避免 Workflow 被錯誤測試或部署。

## 3.5 Testing Context

### Responsibility

Testing Context 負責執行 Workflow Test，並記錄測試過程與測試結果。

### Main Concepts

* Test Scenario
* Test Run
* Test Result
* Execution Record
* Agent
* Workflow

### Main Use Cases

* UC-06 Run Workflow Test

### Description

Tester 可以選擇已通過相容性驗證的 Agent 與 Workflow，並在指定 Test Scenario 中執行測試。測試後系統會產生 Test Run 與 Execution Record，記錄執行狀態、執行時間、錯誤資訊與資源使用量。

## 3.6 Deployment Context

### Responsibility

Deployment Context 負責將已通過驗證與必要測試的 Workflow 部署到指定 Agent。

### Main Concepts

* Deployment
* Deployment Status
* Agent
* Workflow
* Execution Record

### Main Use Cases

* UC-07 Deploy Workflow to Agent

### Description

Deployment 表示將 Workflow 正式交給 Agent 執行的流程。系統應確認 Workflow 與 Agent 狀態皆符合部署條件後，才允許部署。部署結果也會被記錄，供後續 evaluation 使用。

## 3.7 Evaluation Context

### Responsibility

Evaluation Context 負責根據測試或部署後的 Execution Record，進行效能與能源消耗評估。

### Main Concepts

* Execution Record
* Performance Evaluation
* Energy Evaluation
* Evaluation Report

### Main Use Cases

* UC-08 Evaluate Performance
* UC-09 Evaluate Energy Consumption

### Description

Evaluator 可以根據測試或部署紀錄分析 Agent 與 Workflow 的表現。Performance Evaluation 主要分析 execution time、success rate 與 resource usage。Energy Evaluation 則分析 energy consumption，協助判斷哪一個 Agent 或 Workflow 更節能。

## 4. Entity Analysis

Entity 是具有唯一識別值的物件，即使屬性改變，只要 ID 相同，就代表同一個物件。

## 4.1 Robot

### Type

Entity

### Identity

robotId

### Main Attributes

* robotId
* name
* type
* hardwareSpecification
* sensors
* actuators
* capabilities

### Responsibility

Robot 負責表示一個實際或模擬的機器人裝置，並保存該裝置的硬體規格、感測器、致動器與能力資料。

### Related Use Case

* UC-01 Register Robot

## 4.2 Agent

### Type

Entity

### Identity

agentId

### Main Attributes

* agentId
* robotId
* name
* state
* capabilities

### Responsibility

Agent 負責表示由 Robot 抽象出來的共同執行模型。Agent 可以被用來執行 Workflow Test、Deployment 與 Evaluation。

### Related Use Case

* UC-02 Agent Abstraction

## 4.3 Task

### Type

Entity

### Identity

taskId

### Main Attributes

* taskId
* name
* goal
* requiredCapabilities
* constraints
* expectedResult

### Responsibility

Task 負責表示一個可執行的任務單位。每個 Task 會描述任務目標、所需能力、限制條件與預期結果。

### Related Use Case

* UC-03 Define Task

## 4.4 Workflow

### Type

Entity, Aggregate Root

### Identity

workflowId

### Main Attributes

* workflowId
* name
* description
* tasks
* status

### Responsibility

Workflow 負責管理多個 Task 的組合與執行順序。Workflow 是 Task and Workflow Context 中最重要的 aggregate root，後續相容性驗證、測試與部署都會以 Workflow 為核心。

### Related Use Case

* UC-04 Create Workflow

## 4.5 CompatibilityValidation

### Type

Entity

### Identity

validationId

### Main Attributes

* validationId
* workflowId
* agentId
* result
* missingCapabilities
* validatedAt

### Responsibility

CompatibilityValidation 負責記錄某個 Agent 與某個 Workflow 的相容性驗證結果。

### Related Use Case

* UC-05 Validate Workflow Compatibility

## 4.6 TestScenario

### Type

Entity

### Identity

scenarioId

### Main Attributes

* scenarioId
* name
* description
* parameters

### Responsibility

TestScenario 負責描述 Workflow Test 的測試情境與測試參數。

### Related Use Case

* UC-06 Run Workflow Test

## 4.7 TestRun

### Type

Entity, Aggregate Root

### Identity

testRunId

### Main Attributes

* testRunId
* workflowId
* agentId
* scenarioId
* status
* startedAt
* endedAt
* executionRecord

### Responsibility

TestRun 負責表示一次 Workflow Test 的執行過程與結果。

### Related Use Case

* UC-06 Run Workflow Test

## 4.8 Deployment

### Type

Entity, Aggregate Root

### Identity

deploymentId

### Main Attributes

* deploymentId
* workflowId
* agentId
* status
* deployedAt
* executionRecord

### Responsibility

Deployment 負責記錄 Workflow 部署到 Agent 的過程與結果。

### Related Use Case

* UC-07 Deploy Workflow to Agent

## 4.9 ExecutionRecord

### Type

Entity

### Identity

recordId

### Main Attributes

* recordId
* sourceType
* sourceId
* executionTime
* successRate
* resourceUsage
* energyConsumption
* status

### Responsibility

ExecutionRecord 負責記錄測試或部署後產生的執行資料，並作為 Performance Evaluation 與 Energy Evaluation 的資料來源。

### Related Use Cases

* UC-06 Run Workflow Test
* UC-07 Deploy Workflow to Agent
* UC-08 Evaluate Performance
* UC-09 Evaluate Energy Consumption

## 4.10 PerformanceEvaluation

### Type

Entity

### Identity

performanceEvaluationId

### Main Attributes

* performanceEvaluationId
* recordId
* executionTime
* successRate
* resourceUsage
* result

### Responsibility

PerformanceEvaluation 負責根據 ExecutionRecord 分析 workflow 的效能表現。

### Related Use Case

* UC-08 Evaluate Performance

## 4.11 EnergyEvaluation

### Type

Entity

### Identity

energyEvaluationId

### Main Attributes

* energyEvaluationId
* recordId
* energyConsumption
* energyEfficiency
* result

### Responsibility

EnergyEvaluation 負責根據 ExecutionRecord 分析 workflow 的能源消耗表現。

### Related Use Case

* UC-09 Evaluate Energy Consumption

## 4.12 EvaluationReport

### Type

Entity, Aggregate Root

### Identity

reportId

### Main Attributes

* reportId
* performanceEvaluation
* energyEvaluation
* generatedAt
* summary

### Responsibility

EvaluationReport 負責整合效能評估與能源評估結果，並產生可供 Evaluator 查看或比較的報告。

### Related Use Cases

* UC-08 Evaluate Performance
* UC-09 Evaluate Energy Consumption

## 5. Value Object Analysis

Value Object 沒有獨立 identity，主要用屬性內容判斷是否相同。

| Value Object          | Description                                    |
| --------------------- | ---------------------------------------------- |
| HardwareSpecification | Robot 的硬體規格，例如 CPU、memory、battery、device type。 |
| Sensor                | Robot 的感測器資訊，例如 sensor name、sensor type。       |
| Actuator              | Robot 的致動器資訊，例如 actuator name、actuator type。   |
| Capability            | Robot 或 Agent 的能力，例如 move、camera、arm_control。  |
| AgentState            | Agent 的狀態，例如 idle、testing、deployed、error。      |
| Goal                  | Task 的任務目標。                                    |
| Constraint            | Task 執行時需要遵守的限制條件。                             |
| ExpectedResult        | Task 預期完成後的結果。                                 |
| ValidationResult      | 相容性驗證結果，例如 compatible、incompatible。            |
| TestParameter         | 測試時使用的參數設定。                                    |
| ExecutionTime         | 執行時間。                                          |
| ResourceUsage         | 資源使用量。                                         |
| EnergyConsumption     | 能源消耗資料。                                        |
| DeploymentStatus      | 部署狀態，例如 pending、deployed、failed。               |

其中 Capability 在本專案第一階段可以視為 Value Object，因為它主要用名稱或類型進行比對。如果未來系統需要獨立管理 Capability，例如新增 capability database、capability version 或 capability dependency，則可以改為 Entity。

## 6. Aggregate Design

Aggregate 是一組需要一起維護一致性的 domain objects。Aggregate Root 是外部可以直接操作的主要物件。

## 6.1 Robot Aggregate

### Aggregate Root

Robot

### Included Objects

* HardwareSpecification
* Sensor
* Actuator
* Capability

### Consistency Rules

1. Robot 必須有唯一 robotId。
2. Robot name 不可重複。
3. Robot 必須至少定義基本硬體資訊。
4. Robot 的 capabilities 必須符合平台支援的 capability 格式。
5. Robot 的 sensors、actuators 與 capabilities 需要被正確儲存，因為會影響後續 Agent 建立。

## 6.2 Agent Aggregate

### Aggregate Root

Agent

### Included Objects

* AgentState
* Capability

### Consistency Rules

1. Agent 必須由已註冊的 Robot 建立。
2. Agent 的 capabilities 必須來自對應 Robot 的 capabilities。
3. Agent 建立後應有初始狀態，例如 idle。
4. Agent 若處於 unavailable 或 error 狀態，不應被用於測試或部署。

## 6.3 Workflow Aggregate

### Aggregate Root

Workflow

### Included Objects

* Task
* RequiredCapability
* Constraint
* ExpectedResult
* WorkflowStatus

### Consistency Rules

1. Workflow 必須至少包含一個 Task。
2. Workflow 中的 Task 必須有明確順序。
3. Workflow 中的 Task 必須完成必要設定。
4. Workflow 在相容性驗證前應處於 ready 狀態。
5. Workflow 若不相容，不應進入測試或部署流程。

## 6.4 TestRun Aggregate

### Aggregate Root

TestRun

### Included Objects

* TestScenario
* ExecutionRecord
* TestResult

### Consistency Rules

1. TestRun 必須指定一個 Agent 與一個 Workflow。
2. TestRun 的 Workflow 必須已通過 compatibility validation。
3. TestRun 開始後應記錄 startedAt。
4. TestRun 結束後應記錄 endedAt 與 ExecutionRecord。
5. TestRun 應保存成功或失敗狀態。

## 6.5 Deployment Aggregate

### Aggregate Root

Deployment

### Included Objects

* DeploymentStatus
* ExecutionRecord

### Consistency Rules

1. Deployment 必須指定一個 Agent 與一個 Workflow。
2. Workflow 必須已通過相容性驗證。
3. Workflow 應通過必要測試後才能部署。
4. Deployment 應記錄部署結果與狀態。
5. 若部署失敗，系統應保留失敗原因。

## 6.6 EvaluationReport Aggregate

### Aggregate Root

EvaluationReport

### Included Objects

* PerformanceEvaluation
* EnergyEvaluation

### Consistency Rules

1. EvaluationReport 必須根據有效的 ExecutionRecord 產生。
2. PerformanceEvaluation 必須使用 execution time、success rate 或 resource usage 等資料。
3. EnergyEvaluation 必須使用 energy consumption 資料。
4. 若執行資料不足，系統不應產生不完整的評估結果。

## 7. Domain Services

Domain Service 用來處理不適合放在單一 Entity 或 Value Object 裡的 domain logic。

| Domain Service                 | Responsibility                                                      |
| ------------------------------ | ------------------------------------------------------------------- |
| AgentCreationService           | 根據已註冊的 Robot 建立 Agent，並轉換 capabilities。                             |
| CompatibilityValidationService | 比對 Workflow 所需能力與 Agent capabilities，產生 validation result。          |
| WorkflowExecutionService       | 控制 Workflow 依照 Task 順序執行測試。                                         |
| DeploymentService              | 負責將 Workflow 部署到指定 Agent，並記錄部署狀態。                                   |
| PerformanceEvaluationService   | 根據 ExecutionRecord 計算 execution time、success rate 與 resource usage。 |
| EnergyEvaluationService        | 根據 ExecutionRecord 計算 energy consumption 與 energy efficiency。       |
| EvaluationReportService        | 整合 performance 與 energy evaluation，產生 evaluation report。            |

## 8. Repository Design

Repository 負責儲存與讀取 aggregate 或重要 entity。

| Repository                        | Main Responsibility      |
| --------------------------------- | ------------------------ |
| RobotRepository                   | 儲存與查詢 Robot profiles。    |
| AgentRepository                   | 儲存與查詢 Agent models。      |
| TaskRepository                    | 儲存與查詢 Task。              |
| WorkflowRepository                | 儲存與查詢 Workflow。          |
| CompatibilityValidationRepository | 儲存與查詢相容性驗證結果。            |
| TestScenarioRepository            | 儲存與查詢 Test Scenario。     |
| TestRunRepository                 | 儲存與查詢 Test Run。          |
| DeploymentRepository              | 儲存與查詢 Deployment。        |
| ExecutionRecordRepository         | 儲存與查詢 Execution Record。  |
| EvaluationReportRepository        | 儲存與查詢 Evaluation Report。 |

## 9. Domain Relationships

本系統主要 domain objects 的關係如下：

1. 一個 Robot 可以擁有多個 Sensor。
2. 一個 Robot 可以擁有多個 Actuator。
3. 一個 Robot 可以擁有多個 Capability。
4. 一個 Robot 可以建立一個或多個 Agent。
5. 一個 Agent 來源於一個 Robot。
6. 一個 Task 可以需要多個 Capability。
7. 一個 Workflow 由一個或多個 Task 組成。
8. 一個 Workflow 可以和一個 Agent 進行 Compatibility Validation。
9. 一個 TestRun 會關聯一個 Workflow、一個 Agent 與一個 TestScenario。
10. 一個 Deployment 會關聯一個 Workflow 與一個 Agent。
11. 一個 TestRun 或 Deployment 可以產生一筆 ExecutionRecord。
12. 一筆 ExecutionRecord 可以被用來產生 PerformanceEvaluation。
13. 一筆 ExecutionRecord 可以被用來產生 EnergyEvaluation。
14. 一份 EvaluationReport 可以包含 PerformanceEvaluation 與 EnergyEvaluation。

## 10. Domain Workflow

本系統的主要 domain flow 如下：

1. Robotic Developer 註冊 Robot。
2. 系統儲存 Robot profile。
3. Robotic Developer 根據 Robot 建立 Agent。
4. Robotic Developer 定義 Task。
5. Robotic Developer 將多個 Task 組成 Workflow。
6. 系統驗證 Agent 是否具備 Workflow 所需 capabilities。
7. 若相容，Tester 可以執行 Workflow Test。
8. 系統產生 TestRun 與 ExecutionRecord。
9. 若測試通過，Robotic Developer 可以將 Workflow 部署至 Agent。
10. 系統產生 Deployment 與 ExecutionRecord。
11. Evaluator 根據 ExecutionRecord 進行 Performance Evaluation。
12. Evaluator 根據 ExecutionRecord 進行 Energy Evaluation。
13. 系統產生 Evaluation Report。

## 11. Domain Rules

1. Robot 必須先註冊，才能建立 Agent。
2. Agent 必須基於已存在的 Robot 建立。
3. Task 必須定義 goal、required capabilities、constraints 與 expected result。
4. Workflow 必須至少包含一個 Task。
5. Workflow 中的 Task 必須有明確順序。
6. Workflow 在測試前必須先通過 compatibility validation。
7. Agent 必須具備 Workflow 所有必要 capabilities，才可被視為 compatible。
8. 不相容的 Workflow 不應被測試或部署。
9. Workflow 應完成必要測試後再部署。
10. TestRun 與 Deployment 都應產生 ExecutionRecord。
11. Performance Evaluation 必須根據有效 ExecutionRecord 產生。
12. Energy Evaluation 必須根據有效 energy consumption data 產生。
13. Evaluation Report 不應使用不完整或無效的執行資料。

## 12. Domain Events

Domain Event 用來表示系統中已經發生的重要業務事件。

| Domain Event              | Trigger                        |
| ------------------------- | ------------------------------ |
| RobotRegistered           | Robot 成功註冊後發生。                 |
| AgentCreated              | Agent 成功建立後發生。                 |
| TaskDefined               | Task 成功定義後發生。                  |
| WorkflowCreated           | Workflow 成功建立後發生。              |
| CompatibilityValidated    | Workflow 與 Agent 完成相容性驗證後發生。   |
| WorkflowTestStarted       | Tester 開始執行 Workflow Test 時發生。 |
| WorkflowTestCompleted     | Workflow Test 完成後發生。           |
| WorkflowDeployed          | Workflow 成功部署到 Agent 後發生。      |
| ExecutionRecorded         | 測試或部署結果被記錄後發生。                 |
| PerformanceEvaluated      | 效能評估完成後發生。                     |
| EnergyEvaluated           | 能源評估完成後發生。                     |
| EvaluationReportGenerated | 評估報告產生後發生。                     |

## 13. State Models

## 13.1 Agent State

| State    | Description                 |
| -------- | --------------------------- |
| IDLE     | Agent 已建立，目前未執行任何 workflow。 |
| TESTING  | Agent 正在執行 workflow test。   |
| DEPLOYED | Agent 已被部署 workflow。        |
| ERROR    | Agent 發生錯誤，暫時不可用。           |

## 13.2 Workflow State

| State     | Description            |
| --------- | ---------------------- |
| DRAFT     | Workflow 尚未完成設定。       |
| READY     | Workflow 已完成設定，可以進行驗證。 |
| VALIDATED | Workflow 已通過相容性驗證。     |
| TESTED    | Workflow 已完成測試。        |
| DEPLOYED  | Workflow 已部署到 Agent。   |

## 13.3 TestRun Status

| Status  | Description |
| ------- | ----------- |
| PENDING | 測試尚未開始。     |
| RUNNING | 測試執行中。      |
| SUCCESS | 測試成功完成。     |
| FAILED  | 測試失敗。       |

## 13.4 Deployment Status

| Status      | Description |
| ----------- | ----------- |
| PENDING     | 部署尚未開始。     |
| DEPLOYED    | 部署成功。       |
| FAILED      | 部署失敗。       |
| ROLLED_BACK | 部署已回復。      |

## 14. Context Map

本系統的 context map 可以用文字描述如下：

1. Robot Management Context 提供 Robot profile 給 Agent Management Context。
2. Agent Management Context 根據 Robot profile 建立 Agent。
3. Task and Workflow Context 提供 Workflow 與 Task requirements 給 Compatibility Validation Context。
4. Compatibility Validation Context 同時依賴 Agent capabilities 與 Workflow required capabilities。
5. Testing Context 只允許使用已通過 compatibility validation 的 Agent 與 Workflow。
6. Deployment Context 使用已通過驗證與測試的 Workflow。
7. Testing Context 與 Deployment Context 會產生 ExecutionRecord。
8. Evaluation Context 讀取 ExecutionRecord，並產生 PerformanceEvaluation、EnergyEvaluation 與 EvaluationReport。

## 15. Summary

本系統的 domain model 以 Robot、Agent、Task、Workflow、Testing、Deployment 與 Evaluation 為核心。Robot 是實際裝置的資料來源，Agent 是統一抽象模型，Task 與 Workflow 負責描述任務流程，Compatibility Validation 負責防止不相容流程被錯誤執行，Testing 與 Deployment 負責實際執行流程，Evaluation 則根據執行紀錄分析效能與能源消耗。

透過 DDD 的 bounded context、entity、value object、aggregate、domain service 與 repository 設計，本系統可以讓 robotic agent 的測試與部署流程更清楚、更容易維護，也更容易擴充到不同類型的 Robot 與 Workflow。
