# Domain-Driven Design (DDD) — 5W1H 與 8C

**日期**：2026-06-06

---

## 5W1H

- **誰 (Who)**：領域專家、產品經理、開發者、架構師、測試人員。
- **什麼 (What)**：Domain-Driven Design（DDD）——以業務領域為核心、用模型驅動設計軟體的實務與方法論。
- **何時 (When)**：系統具有高度業務複雜性、需長期演進或多團隊協作時採用。
- **何地 (Where)**：企業級核心業務、複雜領域邏輯、以及跨團隊邊界的後端系統或服務。
- **為何 (Why)**：對齊軟體與業務語言、降低溝通成本、掌控複雜性、提升可維護性與長期演進能力。
- **如何 (How)**：與領域專家密切合作、建立 Ubiquitous Language、劃分 Bounded Context、採用 Entities/Value Objects/Aggregates/Domain Events 等戰術模式，並持續重構與測試。

---

## 8C（DDD 重點）

- **Context（上下文）**：明確劃分 Bounded Context，界定模型邊界與整合契約（API、事件、反腐層）。
- **Core（核心）**：聚焦 Core Domain，把有限的工程資源放在最具價值的領域。
- **Collaboration（協作）**：開發者與領域專家共同建模，透過討論、範例與事件序列化需求。
- **Communication（溝通）**：建立並維護 Ubiquitous Language，讓語言成為跨職能的共同事實來源。
- **Consistency（一致性）**：在 Context 內維持模型一致性，跨 Context 採用最合適的一致性策略（事件最終一致性、補償流程）。
- **Complexity（複雜性）**：用界限、聚合與領域服務等設計來管理複雜性，而非把複雜度散落到基礎設施。
- **Components（組件）**：利用 Entities、Value Objects、Aggregates、Repositories、Domain Events 等封裝領域概念。
- **Continuous（持續演進）**：透過重構、測試與事件驅動，不斷演化模型以配合業務變化。

---

如果需要，我可以把此檔案放入子目錄、或加上範例程式碼與引用資源列表。
