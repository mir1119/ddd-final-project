# 05. AI Benefit Evaluation

## 1. Evaluation Overview

In this project, we used AI tools to assist the development of the Agentic Testing Framework. AI was mainly used in requirement analysis, domain modeling, system design, coding, debugging, and documentation.

本專案使用 AI 輔助開發 Agentic Testing Framework。AI 協助我們完成需求分析整理、DDD 領域模型設計、系統設計、程式實作、除錯與 GitHub 文件撰寫。

透過這次專案，我們最明顯感受到的是：AI 可以大幅加快開發速度。以前很多繁重、重複性高的 coding 工作，例如建立 class、補 method、整理資料結構、撰寫簡單邏輯與文件格式，現在都可以透過 AI 很快產生初稿，讓開發者把時間花在更重要的設計與判斷上。

但是，這不代表軟體工程沒有存在的必要性。相反地，這次專案讓我們更明確感受到：如果前面沒有做好需求分析、系統設計與 DDD context 劃分，後面的 AI-assisted coding 或 vibe coding 其實不會順利。

## 2. Development Speed Improvement

AI 對本專案最大的幫助，是大幅提升開發速度。

在傳統開發流程中，我們需要花很多時間從零開始撰寫 class、method、測試流程與文件內容。例如 Robot、Agent、Task、Workflow、TestRun、Deployment、EvaluationReport 等 class，如果全部手動設計與撰寫，會花費大量時間。

使用 AI 後，我們可以先輸入需求分析與系統設計，再請 AI 協助產生初步程式碼與文件。這讓我們能更快建立 prototype，並快速看到系統流程是否合理。

AI 對開發速度的幫助主要體現在以下幾點：

1. 快速產生 class skeleton。
2. 快速補齊 method 與基本邏輯。
3. 快速整理 Markdown 文件。
4. 快速協助除錯與修正程式結構。
5. 快速將需求分析轉換成系統設計與程式架構。

因此，AI 讓我們不用把大量時間花在重複性 coding 上，而是可以把更多時間用在理解需求、檢查架構與整合系統。

## 3. Reduction of Repetitive Coding Work

以前很多 coding 作業的繁重部分，主要不是概念困難，而是需要花時間寫大量相似的程式碼。例如：

* 建立多個 domain classes
* 撰寫 constructor
* 設定 attributes
* 撰寫 getter / setter
* 建立 service method
* 撰寫簡單 validation logic
* 產生 report output
* 整理 README 或 Markdown documentation

這些工作本身很重要，但在專案初期會消耗大量時間。AI 可以幫助我們快速完成這些基礎工作，讓 prototype 更快成形。

在本專案中，AI 協助我們將 Agentic Testing Framework 的核心概念轉換成 Java 類別，例如 Robot、Agent、Task、Workflow、CompatibilityValidation、TestRun、Deployment、PerformanceEvaluation、EnergyEvaluation 與 EvaluationReport。這讓我們能在短時間內建立可執行的 prototype，並檢查整個流程是否能從 Robot registration 一路執行到 evaluation report。

## 4. Why Software Engineering Is Still Necessary

雖然 AI 能讓開發速度變快，但這並不代表軟體工程變得不重要。

相反地，這次專案讓我們發現：AI 越強，前期的軟體工程分析越重要。

如果我們沒有先做需求分析，AI 不會知道系統到底要解決什麼問題。如果我們沒有先定義 use cases，AI 可能會產生不符合需求的功能。如果我們沒有先做系統設計，AI 產生的程式碼可能會變得混亂、不一致，甚至難以整合。

因此，AI 並不是取代軟體工程，而是放大軟體工程的效果。

本專案中，我們先完成：

1. Requirement Analysis
2. User Stories
3. Use Case Description
4. Domain Analysis
5. DDD Bounded Contexts
6. Class Design
7. System Design
8. Sequence Design

有了這些基礎後，AI 才能更準確地協助我們產生程式碼與文件。

## 5. Importance of Requirement Analysis

需求分析是本專案能順利使用 AI 的重要原因。

在開發前，我們先定義了系統的主要功能：

* Register Robot
* Create Agent
* Define Task
* Create Workflow
* Validate Workflow Compatibility
* Run Workflow Test
* Deploy Workflow to Agent
* Evaluate Performance
* Evaluate Energy Consumption

這些需求讓 AI 能清楚知道系統應該包含哪些功能，也讓我們在使用 AI 產生程式碼時，可以判斷 AI 的輸出是否符合專案目標。

如果沒有這些需求分析，AI 可能會產生過度複雜或偏離主題的功能。例如它可能會加入完整前後端系統、資料庫、真實機器人控制或雲端部署等內容，這些雖然看起來完整，但不一定符合本次期末專案的範圍。

因此，需求分析幫助我們控制 AI 產出的方向。

## 6. Importance of System Design

系統設計讓 AI 產出的程式碼更容易整合。

在本專案中，我們先定義了主要 class 與 method，例如：

| Class                   | Main Methods                              |
| ----------------------- | ----------------------------------------- |
| Robot                   | register(), checkProfile()                |
| Agent                   | createFromRobot(), getStatus()            |
| Task                    | defineTask(), checkRequirement()          |
| Workflow                | createWorkflow(), checkWorkflow()         |
| CompatibilityValidation | validate()                                |
| TestRun                 | runTest(), generateTestReport()           |
| Deployment              | deployWorkflow(), checkDeploymentStatus() |
| ExecutionRecord         | recordExecution()                         |
| PerformanceEvaluation   | evaluatePerformance()                     |
| EnergyEvaluation        | evaluateEnergy()                          |
| EvaluationReport        | generateEvaluationReport()                |

因為已經有清楚的 class design，AI 在協助 coding 時，就比較容易產生符合架構的程式碼。我們也比較容易檢查 AI 產出的內容是否正確。

如果沒有系統設計，AI 可能會用不同的命名方式、不同的資料流或不同的 class structure，導致程式碼雖然單獨看起來可以運作，但整體很難整合。

## 7. Importance of DDD and Bounded Contexts

本專案使用 DDD 來劃分 bounded contexts，這對 AI-assisted development 很有幫助。

我們將系統拆分成：

* Robot Management Context
* Agent Management Context
* Task and Workflow Context
* Compatibility Validation Context
* Testing Context
* Deployment Context
* Evaluation Context

這樣的劃分讓系統責任更清楚，也讓 prompt 更容易撰寫。

例如，當我們要請 AI 協助撰寫 Agent 相關程式時，可以明確告訴 AI 這屬於 Agent Management Context，並且 Agent 是根據 Robot 建立的共同抽象模型。當我們要請 AI 協助撰寫 evaluation 功能時，也可以明確告訴 AI Evaluation Context 只需要根據 ExecutionRecord 產生 performance 與 energy evaluation。

DDD 的 bounded context 幫助我們降低系統複雜度，也讓 AI 不會把不同模組的責任混在一起。

## 8. Vibe Coding Works Better With Design

在這次專案中，我們也體會到 vibe coding 的優點與限制。

Vibe coding 可以讓開發者用自然語言描述想要的功能，再由 AI 協助產生程式碼。這種方式非常快，尤其適合用來建立 prototype 或補齊重複性程式碼。

但是 vibe coding 並不是隨便描述幾句話就能得到好結果。它仍然需要清楚的需求、架構與限制。

本專案中，因為我們前面已經完成需求分析、系統設計與 DDD context 劃分，所以在 vibe coding 階段可以更精準地告訴 AI：

* 要產生哪些 class
* 每個 class 負責什麼
* 哪些 method 需要存在
* 各個 object 之間有什麼關係
* 哪些功能屬於同一個 context
* 哪些內容不在本專案範圍內

因此，vibe coding 的順利並不是因為不需要軟體工程，而是因為前面的軟體工程工作讓 AI 有明確方向可以遵循。

## 9. Comparison Before and After Using AI

| Aspect             | Traditional Development       | AI-Assisted Development        |
| ------------------ | ----------------------------- | ------------------------------ |
| Coding Speed       | 需要手動建立大量 class 與 method，速度較慢。 | AI 可以快速產生初稿，加快 prototype 建立速度。 |
| Documentation      | 需要手動整理需求、設計與 README，耗時較長。     | AI 可以協助整理 Markdown 文件與表格。      |
| Debugging          | 需要自己逐步尋找錯誤原因。                 | AI 可以提供可能的錯誤方向與修正建議。           |
| Design Consistency | 若沒有明確設計，容易寫到後面才發現架構混亂。        | 有系統設計後，AI 可以依照設計產生較一致的程式碼。     |
| Human Role         | 主要負責全部分析、設計、實作與修正。            | 人仍然負責需求判斷、架構決策、驗證與整合。          |
| Risk               | 開發速度慢，但比較容易掌握每段程式碼。           | 開發速度快，但需要檢查 AI 是否產生錯誤或不合理內容。   |

## 10. Limitations of AI in This Project

雖然 AI 很有幫助，但我們也遇到一些限制。

### 10.1 AI May Generate Overly Complex Designs

AI 有時會建議過大的系統架構，例如完整前後端、資料庫、真實機器人連線、雲端部署等。這些設計雖然看起來完整，但對本次期末專案來說太大。因此，我們需要人工判斷哪些功能要保留，哪些功能要刪除。

### 10.2 AI May Use Inconsistent Naming

AI 有時會混用不同名稱，例如 TestRun、WorkflowTest、ExecutionRecord、TestReport 等。如果沒有人工整理，文件和程式碼可能會出現命名不一致的問題。

### 10.3 AI Code Still Needs Review

AI 產生的程式碼不能直接完全相信。它可能有 method 名稱不一致、資料型別不一致、邏輯不完整或不符合專案需求的問題。因此，組員仍然需要實際編譯、執行與修正。

### 10.4 AI Does Not Replace Requirement Understanding

AI 可以幫忙整理需求，但它不會真正知道老師的要求、專案限制、時間限制與組員能力。這些都需要由開發者自己判斷。

## 11. Overall Evaluation

整體而言，AI 對本專案的幫助非常明顯。它讓我們能夠更快完成 prototype、文件與設計整理，也降低了大量重複性 coding 的負擔。

但是，這次經驗也讓我們理解到，AI-assisted development 並不是跳過軟體工程流程。相反地，需求分析、系統設計與 DDD 劃分 context 變得更重要，因為這些前期工作決定了 AI 產出的品質。

如果沒有明確需求，AI 可能會產生錯誤方向的功能。

如果沒有系統設計，AI 可能會產生難以整合的程式碼。

如果沒有 DDD context 劃分，AI 可能會混淆不同模組的責任。

因此，我們認為 AI 最適合扮演「加速器」的角色。它可以加快實作速度、降低重複工作、協助除錯與整理文件，但專案方向、架構設計、需求判斷與品質把關仍然必須由開發者負責。

## 12. Conclusion

Through this project, we learned that AI can significantly improve software development efficiency. Many coding tasks that used to be time-consuming can now be completed much faster with AI support.

However, AI does not make software engineering unnecessary. Instead, it makes software engineering even more important. Good requirement analysis, system design, and DDD-based context separation provide the structure that allows AI-assisted coding to work effectively.

In our project, the reason vibe coding worked smoothly was not because we skipped software engineering, but because we had already spent time defining requirements, designing the system, and organizing the domain model. AI helped us move faster, but software engineering helped us move in the right direction.