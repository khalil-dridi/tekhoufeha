# Sprint 1 Planning

**Project:** TekhouFeha

**Sprint:** Sprint 01

**Version:** 1.0

**Status:** In Progress

---

# 1. Sprint Goal

Build the foundation of the TekhouFeha platform by implementing the authentication and user management modules.

At the end of this sprint, a user should be able to:

- Register an account.
- Login securely.
- Receive a JWT access token.
- Access protected endpoints.
- View their own profile.

This sprint also establishes the technical foundation for all future development.

---

# 2. Sprint Scope

Included:

- API Gateway
- Auth Service
- User Service
- JWT Authentication
- User Registration
- User Login
- Get Current User Profile

Not Included:

- Product Management
- Offers
- Messaging
- Notifications
- Reports
- Administration Dashboard

---

# 3. User Stories

### US-01

As a visitor,

I want to create an account,

So that I can use the platform.

---

### US-02

As a registered user,

I want to login,

So that I can access my account securely.

---

### US-03

As an authenticated user,

I want to view my profile,

So that I can verify my personal information.

---

# 4. Microservices

The following services will be developed during this sprint.

| Service | Status |
|----------|---------|
| API Gateway | Planned |
| Auth Service | Planned |
| User Service | Planned |

---

# 5. Backend Tasks

- Create API Gateway
- Create Auth Service
- Create User Service
- Configure Spring Security
- Configure JWT Authentication
- Configure BCrypt Password Encoder
- Implement Register API
- Implement Login API
- Implement Current User API
- Exception Handling
- Validation
- Swagger Documentation

---

# 6. Frontend Tasks

- Create Angular Project
- Configure Angular Material
- Configure Tailwind CSS
- Create Login Page
- Create Register Page
- Create Home Page
- Authentication Service
- Route Guards
- HTTP Interceptor

---

# 7. Database Tasks

Auth Database

Create authentication tables.

User Database

Create user tables.

---

# 8. Testing Tasks

Backend

- Unit Tests
- Integration Tests

Frontend

- Component Tests

Manual

- Register
- Login
- JWT Authentication
- Profile Retrieval

---

# 9. Deliverables

At the end of Sprint 1, the project must include:

- API Gateway
- Auth Service
- User Service
- Angular Application
- JWT Authentication
- Docker Configuration
- Swagger Documentation
- Passing Tests

---

# 10. Definition of Done

A task is considered completed when:

- Code is implemented.
- Code is tested.
- No compilation errors.
- API documented.
- Git commit created.
- Code pushed to GitHub.

---

# 11. Risks

- Incorrect JWT configuration.
- Communication issues between services.
- Security misconfiguration.
- Docker networking issues.

---

# 12. Sprint Completion Criteria

Sprint 1 is completed when:

- User registration works.
- User login works.
- JWT authentication works.
- Protected endpoints work.
- User profile is accessible.
- All services start successfully.
- Frontend communicates successfully with the backend.

---

# Document History

| Version | Date | Author | Description |
|----------|------|--------|-------------|
| 1.0 | July 2026 | Project Team | Initial version |