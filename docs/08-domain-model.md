# Analysis Class Diagram

## 1. Purpose

The analysis class diagram represents the main business concepts of the TekhouFeha platform and the relationships between them. Unlike the design class diagram, it focuses on the problem domain rather than the technical implementation.

This diagram provides a conceptual view of the system by identifying the core entities involved in the second-hand marketplace and negotiation process.

---

## 2. Analysis Class Diagram

![Analysis Class Diagram](diagrams/analysis-class/AnalysisClassDiagram.jpg)

---

## 3. Main Business Classes

The main business classes identified during the analysis phase are:

- User
- Product
- Category
- Offer
- Conversation
- Message
- Notification
- Report

---

## 4. Relationships

The diagram highlights the following business relationships:

- A **User** publishes one or more **Products**.
- A **Product** belongs to one **Category**.
- A **Product** can receive multiple **Offers**.
- An accepted **Offer** may create a **Conversation**.
- A **Conversation** contains one or more **Messages**.
- A **User** receives **Notifications** related to platform activities.
- A **User** can submit **Reports** to administrators.

---

## 5. Design Decisions

Several important design decisions were made during the analysis phase:

- Buyers and sellers are represented by a single **User** entity.
- Products are organized into categories.
- Negotiation starts with an **Offer** and may continue through a **Conversation**.
- Notifications are considered business entities because they are part of the platform workflow.
- Favorites are not represented as an analysis class because they are treated as a relationship between users and products and will be handled during the design phase if needed.

---

## 6. Notes

This analysis class diagram models the business domain only. It intentionally excludes implementation details such as data types, methods, persistence, and framework-specific concepts. These technical aspects will be introduced in the design class diagram.