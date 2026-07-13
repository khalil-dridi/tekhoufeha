# Auth Service API

**Sprint:** Sprint 01

**Service:** Auth Service

**Status:** In Progress

---

# Overview

The Auth Service is responsible for authentication and authorization.

It provides endpoints for:

- User Registration
- User Login
- JWT Validation
- Current Authenticated User

---

# Endpoints

| Method | Endpoint | Description | Authentication |
|---------|----------|-------------|----------------|
| POST | /api/auth/register | Register a new user | No |
| POST | /api/auth/login | Authenticate a user | No |
| GET | /api/auth/me | Get current authenticated user | Yes |
| POST | /api/auth/validate | Validate JWT Token | Internal |

---

# 1. Register

## Endpoint

POST /api/auth/register

### Request

```json
{
  "firstName": "Mohamed",
  "lastName": "Ali",
  "email": "mohamed@gmail.com",
  "password": "Password123!"
}
```

### Success Response

**HTTP 201 Created**

```json
{
  "success": true,
  "message": "User registered successfully.",
  "data": {
    "id": "UUID"
  },
  "timestamp": "2026-07-13T15:00:00"
}
```

### Possible Errors

- Email already exists
- Invalid email
- Weak password
- Missing required fields

---

# 2. Login

## Endpoint

POST /api/auth/login

### Request

```json
{
  "email": "mohamed@gmail.com",
  "password": "Password123!"
}
```

### Success Response

**HTTP 200 OK**

```json
{
  "success": true,
  "message": "Login successful.",
  "data": {
    "accessToken": "jwt-token",
    "tokenType": "Bearer",
    "expiresIn": 3600
  },
  "timestamp": "2026-07-13T15:00:00"
}
```

### Possible Errors

- Invalid credentials
- User suspended
- User inactive

---

# 3. Get Current User

## Endpoint

GET /api/auth/me

### Authentication

Bearer Token Required

### Success Response

**HTTP 200 OK**

```json
{
  "success": true,
  "message": "Authenticated user retrieved successfully.",
  "data": {
    "id": "UUID",
    "email": "mohamed@gmail.com",
    "role": "USER",
    "status": "ACTIVE"
  },
  "timestamp": "2026-07-13T15:00:00"
}
```

### Possible Errors

- Invalid token
- Expired token
- Unauthorized

---

# 4. Validate Token

## Endpoint

POST /api/auth/validate

### Authentication

Internal endpoint.

Used by API Gateway or other microservices.

### Request

```json
{
  "token": "jwt-token"
}
```

### Success Response

**HTTP 200 OK**

```json
{
  "success": true,
  "message": "Token is valid.",
  "data": {
    "valid": true
  },
  "timestamp": "2026-07-13T15:00:00"
}
```

### Possible Errors

- Invalid token
- Expired token

---

# HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | OK |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 500 | Internal Server Error |