# AI Prompt Log

## Prompt 1

使用者要求：

> 我們已經完成所有的系統設計了，而且已經有 class diagram。最後一步是把實作 code 完成，要根據之前從需求分析到系統設計的設計準則，以及 DDD 的設計，把 code 產出。先閱讀需求分析和系統設計 Word 檔後再實作 code，使用 Java 語言。

## AI 實作策略

1. 閱讀需求分析與系統設計文件。
2. 依照 DDD bounded context 分層：Robot、Agent、Task/Workflow、Compatibility Validation、Testing、Deployment、Evaluation。
3. 將 class diagram 中的主要類別轉成 Java 類別。
4. 讓 domain model 保留核心業務規則，例如：
   - Robot 註冊前檢查 profile 完整性。
   - Agent 必須由已註冊 Robot 建立。
   - Workflow 必須包含 Task，且 Task requirement 必須完整。
   - CompatibilityValidation 比對 Agent capabilities 與 Workflow required capabilities。
   - TestRun 只能在相容時執行。
   - Deployment 必須在相容且測試成功後執行。
   - PerformanceEvaluation 與 EnergyEvaluation 由 ExecutionRecord 計算。
5. 使用 in-memory repository 讓系統可直接執行，避免額外資料庫設定造成 demo 困難。
