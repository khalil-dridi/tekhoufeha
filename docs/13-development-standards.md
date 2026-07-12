# Development Standards

**Project:** TekhouFeha

**Version:** 1.0

**Status:** Approved

---

# 1. Project Overview

## Project Name

TekhouFeha

## Project Type

Marketplace for buying and selling second-hand products.

## Architecture Style

Microservices Architecture.

## Development Methodology

Agile Scrum.

Development is organized into sprints.

Each feature follows the same workflow:

1. User Story
2. Sequence Diagram
3. Backend Development
4. Frontend Development
5. Testing
6. Git Commit
7. Git Push

---

# 2. Technology Stack

## Backend

- Java 21 LTS
- Spring Boot
- Spring Cloud Gateway
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Frontend

- Angular
- Angular Material
- Tailwind CSS
- TypeScript

## Database

- MySQL 8

## Authentication

- JWT Authentication

## API

- REST API
- JSON

## Documentation

- OpenAPI / Swagger

## Version Control

- Git
- GitHub

## Containerization

- Docker
- Docker Compose

## Future Technologies

- Eureka Server
- Spring Cloud Config
- RabbitMQ
- Kubernetes
- CI/CD

---

# 3. Architecture

The application follows a Microservices Architecture.

Each service has:

- its own database
- its own business logic
- its own REST API

Initial services:

- API Gateway
- Auth Service
- User Service
- Product Service
- Offer Service
- Messaging Service
- Notification Service
- Moderation Service

---

# 4. Backend Standards

## Programming Language

Java 21 LTS.

## Build Tool

Maven.

## Framework

Spring Boot.

## ORM

Hibernate with Spring Data JPA.

## Dependency Injection

Spring IoC.

## Validation

Jakarta Validation.

## Object Mapping

MapStruct (if needed).

## Logging

SLF4J + Logback.

## API Documentation

Swagger/OpenAPI.

---

# 5. Frontend Standards

Framework:

Angular

Styling:

- Angular Material
- Tailwind CSS

Architecture:

Feature-based architecture.

Main folders:

- core
- shared
- features
- layouts
- guards
- interceptors

Communication:

REST APIs.

---

# 6. Database Standards

Database:

MySQL.

One database per microservice.

Primary keys:

Long AUTO_INCREMENT.

Naming Convention:

snake_case.

Foreign Keys:

Used only inside the same microservice database.

No cross-service foreign keys.

---

# 7. API Standards

Architecture:

REST.

Data format:

JSON.

HTTP Methods:

GET

POST

PUT

PATCH

DELETE

HTTP Status Codes:

200 OK

201 Created

204 No Content

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

409 Conflict

500 Internal Server Error

---

# 8. Security Standards

Authentication:

JWT.

Passwords:

BCrypt.

Authorization:

Role Based Access Control (RBAC).

Roles:

- USER
- ADMIN

Sensitive information must never be exposed.

---

# 9. Git Standards

Branch Strategy

main

develop

feature/*

bugfix/*

release/*

hotfix/*

Commit Convention

feat:

fix:

refactor:

docs:

style:

test:

chore:

Examples:

feat(auth): implement login

fix(product): validate category

docs: update architecture

---

# 10. Docker Standards

Each microservice has its own Dockerfile.

Development environment uses Docker Compose.

Containers:

- Gateway
- Services
- MySQL databases

Images must remain lightweight.

---

# 11. Testing Standards

Backend

JUnit 5

Mockito

Frontend

Angular Testing

Future

Integration Tests

API Tests

End-to-End Tests

---

# 12. Code Quality Standards

Follow SOLID principles.

Follow Clean Code principles.

Single Responsibility Principle.

Avoid duplicated code.

Meaningful naming.

Small methods.

Proper exception handling.

DTOs must be used between API and business layer.

Business logic must never be inside Controllers.

Repositories only access the database.

Services contain business logic.

Controllers expose REST endpoints only.

---

# 13. Naming Conventions

## Java Classes

PascalCase

Example:

UserService

ProductController

OfferRepository

## Variables

camelCase

Example:

userName

productPrice

offerStatus

## Packages

Lowercase.

Example:

controller

service

repository

entity

dto

mapper

config

security

validation

exception

---

# 14. Project Principles

The project must remain:

- Maintainable
- Scalable
- Readable
- Testable
- Secure
- Modular

Every architectural decision should be justified.

Avoid over-engineering.

Build only what provides value to the MVP.

Code should be understandable by another developer without additional explanation.

---

# Document History

| Version | Date | Author | Description |
|----------|------|--------|-------------|
| 1.0 | July 2026 | Project Team | Initial version |