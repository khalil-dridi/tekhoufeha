# Development Environment

**Project:** TekhouFeha

**Version:** 1.0

**Status:** Approved

---

# 1. Purpose

This document describes the software and tools required to develop, test, and run the TekhouFeha project.

The objective is to ensure that every developer works in the same environment.

---

# 2. Operating System

Recommended:

- Windows 11
- Ubuntu 24.04 LTS
- macOS

Current development environment:

- Windows 11

---

# 3. Backend Environment

Programming Language

- Java 21 LTS

Build Tool

- Apache Maven 3.9+

Framework

- Spring Boot

IDE

- IntelliJ IDEA Ultimate
- IntelliJ IDEA Community (Supported)

---

# 4. Frontend Environment

Framework

- Angular

Language

- TypeScript

Package Manager

- npm

IDE

- Visual Studio Code

Recommended Extensions

- Angular Language Service
- ESLint
- Prettier
- GitLens
- Docker
- Material Icon Theme

---

# 5. Database

Database

- MySQL 8 (Docker)

Database Client

- MySQL Workbench

Each microservice owns its own database.

---

# 6. Containerization

Docker Desktop

Docker Compose

The development environment runs inside Docker containers whenever possible.

---

# 7. API Testing

Recommended Tool

- Postman

Alternative

- Bruno

---

# 8. Version Control

Git

Repository Hosting

GitHub

---

# 9. Required Software

| Software | Version |
|----------|----------|
| Java | 21 LTS |
| Maven | 3.9+ |
| Node.js | 18+ |
| npm | 10+ |
| Angular CLI | Latest Stable |
| Docker Desktop | Latest Stable |
| Docker Compose | Latest Stable |
| Git | Latest Stable |
| MySQL | 8 |

---

# 10. Environment Variables

Sensitive configuration must never be hardcoded.

Examples:

- Database URL
- Database Username
- Database Password
- JWT Secret
- Mail Credentials

Environment variables will be used for:

- Local Development
- Docker
- Production

---

# 11. Development Workflow

The development process follows this order:

1. Pull latest changes
2. Implement feature
3. Test locally
4. Commit changes
5. Push to GitHub

---

# 12. Project Execution

Backend

Spring Boot

Frontend

Angular CLI

Databases

Docker

Future

Entire project will run using Docker Compose.

---

# 13. Best Practices

- Keep software updated.
- Never commit secrets.
- Use Docker for databases.
- Use the same Java version across all services.
- Keep dependencies up to date.
- Test before every commit.

---

# Document History

| Version | Date | Author | Description |
|----------|------|--------|-------------|
| 1.0 | July 2026 | Project Team | Initial version |