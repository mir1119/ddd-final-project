# 01. Requirements

## 1. Project Overview

Agentic Testing Framework 是一個用於 robotic agent 的測試與部署框架。本專案希望將不同類型的 Robot 抽象成共同的 Agent model，並提供 Task 定義、Workflow 建立、相容性驗證、測試執行、部署，以及效能與能源評估功能。

本系統的主要目標是讓 Robotic Developer、Tester 與 Evaluator 可以在同一個平台中管理不同機器人、設計任務流程、執行測試、部署 workflow，並根據執行結果分析 performance 與 energy consumption。

## 2. Background and Motivation

隨著 AI 技術與 edge computing 能力提升，越來越多具身智能裝置被應用於真實或模擬環境中。不同 robotic devices 通常具有不同的 hardware specifications、sensors、actuators、capabilities、運算能力與能源限制。

如果缺乏統一的測試與部署平台，開發者會遇到以下問題：

1. 不同機器人資料格式不一致，難以統一管理。
2. 任務需求與機器人能力難以正確比對。
3. Workflow 缺乏結構化設計，難以重複使用與維護。
4. 不相容的 workflow 可能被錯誤測試或部署。
5. 測試與部署結果缺乏標準化紀錄，難以比較。
6. Performance 與 energy consumption 難以量化與分析。
7. 系統概念變多後，若沒有清楚的 domain model，後續會難以維護與擴充。

因此，本專案採用 Domain-Driven Design（DDD）的概念，將 Robot、Agent、Task、Workflow、Testing、Deployment、Evaluation 等核心概念明確化，並透過 bounded contexts 管理不同領域責任。

## 3. Stakeholders

| Role | Description |
|---|---|
| Robotic Developer | 負責註冊 Robot、建立 Agent、定義 Task、建立 Workflow、驗證相容性與部署 Workflow。 |
| Tester | 負責選擇 Agent 與 Workflow，設定測試情境並執行 Workflow Test。 |
| Evaluator | 負責查看執行紀錄，評估 performance 與 energy consumption，並產生評估報告。 |

## 4. User Stories

### US-001 Register Robot

As a Robotic Developer,  
I want to register different robots with their hardware specifications, sensors, actuators, and capabilities,  
so that the system can manage different robotic devices in a unified way.

身為 Robotic Developer，我希望可以註冊不同機器人的硬體規格、感測器、致動器與能力，以便系統能以統一方式管理不同 robotic devices。

### US-002 Agent Abstraction

As a Robotic Developer,  
I want to create an Agent based on a registered robot,  
so that the robot can be represented as a common Agent model in the system.

身為 Robotic Developer，我希望可以根據已註冊的 Robot 建立 Agent，以便系統可以用共同的 Agent 模型表示不同機器人。

### US-003 Define Task

As a Robotic Developer,  
I want to define a task with a goal, required capabilities, constraints, and expected result,  
so that the Agent can understand what needs to be executed.

身為 Robotic Developer，我希望可以定義一個包含目標、所需能力、限制條件與預期結果的 Task，以便 Agent 可以知道要執行什麼。

### US-004 Create Workflow

As a Robotic Developer,  
I want to combine multiple tasks into a workflow,  
so that I can define a complete mission execution process.

身為 Robotic Developer，我希望可以將多個 Task 組合成 Workflow，以便定義完整的任務執行流程。

### US-005 Validate Workflow Compatibility

As a Robotic Developer,  
I want the system to validate whether an Agent has the required capabilities for a workflow,  
so that incompatible workflows will not be tested or deployed incorrectly.

身為 Robotic Developer，我希望系統可以檢查 Agent 是否具備 Workflow 所需能力，以避免不相容的流程被錯誤測試或部署。

### US-006 Run Workflow Test

As a Tester,  
I want to run a workflow on a selected Agent in a test scenario,  
so that I can observe whether the Agent can complete the mission successfully.

身為 Tester，我希望可以讓指定 Agent 在某個 Test Scenario 中執行 Workflow，以便觀察 Agent 是否能成功完成任務。

### US-007 Deploy Workflow to Agent

As a Robotic Developer,  
I want to deploy a validated workflow to an Agent,  
so that the Agent can execute the workflow in a real or simulated environment.

身為 Robotic Developer，我希望可以將驗證過的 Workflow 部署到 Agent，以便 Agent 可以在真實或模擬環境中執行流程。

### US-008 Evaluate Performance

As an Evaluator,  
I want to evaluate an Agent's execution time, success rate, and resource usage,  
so that I can compare performance across different agents and workflows.

身為 Evaluator，我希望可以評估 Agent 的執行時間、成功率與資源使用量，以便比較不同 Agent 與 Workflow 的效能表現。

### US-009 Evaluate Energy Consumption

As an Evaluator,  
I want to analyze the energy consumption of each test or deployment run,  
so that I can identify which Agent or workflow is more energy-efficient.

身為 Evaluator，我希望可以分析每次測試或部署執行的能源消耗，以便判斷哪個 Agent 或 Workflow 更節能。

## 5. Functional Requirements

| ID | Requirement | Related User Story |
|---|---|---|
| FR-001 | The system shall allow Robotic Developer to register a Robot with name, type, hardware specifications, sensors, actuators, and capabilities. | US-001 |
| FR-002 | The system shall validate Robot registration data and prevent duplicate or incomplete Robot profiles. | US-001 |
| FR-003 | The system shall allow Robotic Developer to create an Agent from a registered Robot. | US-002 |
| FR-004 | The system shall map Robot capabilities to the corresponding Agent model and initialize the Agent state. | US-002 |
| FR-005 | The system shall allow Robotic Developer to define a Task with goal, required capabilities, constraints, and expected result. | US-003 |
| FR-006 | The system shall allow Robotic Developer to create a Workflow by combining multiple Tasks in a defined order. | US-004 |
| FR-007 | The system shall check whether every Task in a Workflow has complete settings before the Workflow is used. | US-004 |
| FR-008 | The system shall validate whether a selected Agent has the required capabilities, sensors, and actuators for a selected Workflow. | US-005 |
| FR-009 | The system shall generate and store a compatibility validation result for later testing or deployment. | US-005 |
| FR-010 | The system shall allow Tester to execute a validated Workflow on a selected Agent under a Test Scenario. | US-006 |
| FR-011 | The system shall record test execution results, including status, execution time, error information, and resource usage. | US-006 |
| FR-012 | The system shall allow Robotic Developer to deploy a validated and tested Workflow to a selected Agent. | US-007 |
| FR-013 | The system shall record deployment status and deployment results. | US-007 |
| FR-014 | The system shall allow Evaluator to evaluate execution performance based on execution time, success rate, and resource usage. | US-008 |
| FR-015 | The system shall allow Evaluator to evaluate energy consumption based on execution records. | US-009 |
| FR-016 | The system shall generate evaluation reports for performance and energy consumption. | US-008, US-009 |
| FR-017 | The system shall allow users to search, filter, or select execution records for evaluation. | US-008, US-009 |

## 6. Non-Functional Requirements

### 6.1 Performance

The system should complete Robot registration, Agent creation, Task definition, Workflow creation, compatibility validation, testing, deployment, and evaluation within a reasonable response time. Since the first version mainly handles structured data and simulated execution records, it should not create heavy computation overhead.

### 6.2 Reliability

The system should correctly store and retrieve Robot, Agent, Task, Workflow, TestRun, Deployment, and Evaluation data. The system should prevent data loss, duplicate registration, incomplete task settings, incorrect capability matching, and invalid workflow deployment.

### 6.3 Security

The system should restrict important operations based on user roles. Only authorized Robotic Developers should create or modify Robot, Agent, Task, Workflow, and Deployment data. Only authorized Testers should execute Workflow Tests. Only authorized Evaluators should access execution records and evaluation reports.

### 6.4 Compatibility

The system should keep capability names and formats consistent across Robot, Agent, Task, Workflow, and Compatibility Validation modules. A Workflow should only proceed to testing or deployment when the selected Agent satisfies the required capabilities.

### 6.5 Maintainability

The system should follow Domain-Driven Design principles. Core concepts such as Robot, Agent, Task, Workflow, TestRun, Deployment, PerformanceEvaluation, and EnergyEvaluation should be clearly separated and easy to maintain.

### 6.6 Extensibility

The system should be able to support additional Robot types, capabilities, tasks, workflows, testing scenarios, evaluation metrics, and deployment environments in the future.

### 6.7 Compliance and Safety

If the system is later connected to real robotic devices, Task and Workflow execution should follow field safety rules and platform operation constraints. In the current project stage, the system focuses on software framework design and does not directly handle real-world legal certification.

## 7. Business Rules

1. A Robot must be registered before it can be used to create an Agent.
2. An Agent must be created from an existing Robot.
3. A Task must define its goal, required capabilities, constraints, and expected result.
4. A Workflow must contain at least one Task.
5. A Workflow should pass compatibility validation before testing.
6. A Workflow should pass compatibility validation and required testing before deployment.
7. If an Agent lacks required capabilities, the Workflow should be marked as incompatible.
8. Test and deployment execution results should be recorded for later evaluation.
9. Performance evaluation should be based on execution time, success rate, and resource usage.
10. Energy evaluation should be based on energy consumption data collected from test or deployment execution records.

## 8. Scope

### 8.1 In Scope

- Register Robot
- Create Agent from Robot
- Define Task
- Create Workflow
- Validate Workflow Compatibility
- Run Workflow Test
- Deploy Workflow to Agent
- Record execution results
- Evaluate Performance
- Evaluate Energy Consumption
- Generate evaluation reports
- Apply DDD concepts to organize domain logic

### 8.2 Out of Scope

- Direct control of real physical robots in the first version
- Real-time hardware sensor integration
- Legal certification for robotic deployment
- Advanced AI task planning
- Large-scale distributed deployment
- Full production-level security system

## 9. Use Case Summary

| Use Case ID | Use Case Name | Actor | Main Purpose |
|---|---|---|---|
| UC-01 | Register Robot | Robotic Developer | Register robotic devices with hardware specifications and capabilities. |
| UC-02 | Create Agent | Robotic Developer | Convert a registered Robot into a common Agent model. |
| UC-03 | Define Task | Robotic Developer | Define task goal, required capabilities, constraints, and expected result. |
| UC-04 | Create Workflow | Robotic Developer | Combine multiple Tasks into a complete mission workflow. |
| UC-05 | Validate Workflow Compatibility | Robotic Developer | Check whether an Agent can execute a Workflow. |
| UC-06 | Run Workflow Test | Tester | Execute a Workflow in a Test Scenario and record results. |
| UC-07 | Deploy Workflow to Agent | Robotic Developer | Deploy a validated Workflow to a selected Agent. |
| UC-08 | Evaluate Performance | Evaluator | Analyze execution time, success rate, and resource usage. |
| UC-09 | Evaluate Energy Consumption | Evaluator | Analyze energy usage of a test or deployment run. |

## 10. Acceptance Criteria

1. The system can register at least one Robot with sensors, actuators, and capabilities.
2. The system can create an Agent from a registered Robot.
3. The system can define Tasks and combine them into a Workflow.
4. The system can validate whether an Agent is compatible with a Workflow.
5. The system can prevent incompatible Workflows from being tested or deployed.
6. The system can run a Workflow Test and produce a TestRun record.
7. The system can deploy a validated Workflow to an Agent.
8. The system can generate performance evaluation results.
9. The system can generate energy consumption evaluation results.
10. The system can produce an evaluation report based on execution records.

## 11. Glossary

| Term | Meaning |
|---|---|
| Robot | A robotic device with hardware specifications, sensors, actuators, and capabilities. |
| Agent | A common system-level abstraction created from a Robot. |
| Capability | A robot or agent ability, such as move, camera, arm control, or navigation. |
| Task | A unit of work with a goal, required capabilities, constraints, and expected result. |
| Workflow | A sequence of Tasks that defines a complete mission execution process. |
| Test Scenario | A testing environment or condition used to execute a Workflow. |
| TestRun | A record of a Workflow test execution. |
| Deployment | The process of assigning a validated Workflow to an Agent. |
| Execution Record | A record produced by test or deployment execution. |
| Performance Evaluation | Evaluation based on execution time, success rate, and resource usage. |
| Energy Evaluation | Evaluation based on energy consumption data. |
shit
