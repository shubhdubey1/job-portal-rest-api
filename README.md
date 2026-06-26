# Job Portal REST API

![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=java&logoColor=white)
![Spring Boot 4.1](https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)

A RESTful API for managing job postings built with **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, and **Spring Security**.

---

## Table of Contents

- [Overview](#overview)
- [Skills Showcased](#skills-showcased)
- [Security Features (Spring Security)](#security-features-spring-security)
- [What I Learned](#what-i-learned)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Entity Model](#entity-model)
- [API Endpoints](#api-endpoints)
- [Authentication & Authorization](#authentication--authorization)
- [Getting Started](#getting-started)
- [API Usage](#api-usage)
- [Project Structure](#project-structure)
- [Author](#author)

---

## Overview

This backend service powers a job portal platform by exposing RESTful endpoints to perform CRUD operations on job postings. Each job posting includes a title, description, required experience, and tech stack. The API also supports keyword-based search across job profiles and descriptions.

The project follows a clean layered architecture: **Controller -> Service -> Repository -> Database**.

**Latest Update:** ✅ Integrated **Spring Security** with database-backed authentication, BCrypt password encryption, and stateless session management — making the API production-ready.

---

## Skills Showcased

| Skill | Implementation Details |
|-------|----------------------|
| REST API Design | Full CRUD with proper HTTP methods (GET, POST, PUT, DELETE) |
| Spring Boot 4.1 | Auto-configuration, embedded server, starter dependencies |
| Spring Data JPA | Repository abstraction with JpaRepository and custom query derivation |
| PostgreSQL | Relational database integration with Spring Data JPA |
| **Spring Security** | Database-backed authentication, BCrypt encryption, stateless security |
| Layered Architecture | Clean separation: Controller, Service, Repository, Entity |
| Lombok | Reduced boilerplate with @Data, @AllArgsConstructor, @NoArgsConstructor |
| Maven Wrapper | Build without local Maven installation using mvnw |
| Environment Config | Database credentials externalized via environment variables |

---

## Security Features (Spring Security)

| Feature | Implementation |
|---------|---------------|
| **Database Authentication** | Custom `UserDetailsService` fetches users from PostgreSQL via JPA — no hardcoded users |
| **BCrypt Password Hashing** | All passwords encrypted with BCrypt (strength factor 12) before storage |
| **Stateless Session Management** | No HTTP sessions — every request is independently authenticated |
| **HTTP Basic Auth** | All endpoints secured via HTTP Basic Authentication |
| **User Registration** | Public `POST /register` endpoint with automatic password encoding |
| **DaoAuthenticationProvider** | Bridges Spring Security with custom `UserDetailsService` and BCrypt encoder |
| **CSRF Disabled** | Disabled for stateless REST API compatibility |

### 🔒 Authentication Flow

1. **User Registration** → Password is hashed with BCrypt → Stored securely in database
2. **User Login** → Credentials sent via HTTP Basic Auth → Validated against database
3. **Request Interception** → `SecurityFilterChain` intercepts every request → Validates credentials
4. **Access Granted** → On success, request proceeds to the controller

---

## What I Learned

Working with Spring Security taught me:

- **UserDetailsService** — How to load user data from a database instead of in-memory storage
- **DaoAuthenticationProvider** — How authentication providers validate credentials
- **BCryptPasswordEncoder** — Why password hashing matters and how to configure strength factors
- **SecurityFilterChain** — How to configure HTTP security, session management, and CSRF protection
- **UserDetails Interface** — How to wrap entity objects to integrate with Spring Security
- **Stateless Architecture** — Why REST APIs should avoid server-side sessions
- **Separation of Concerns** — Keeping security config, user model, and auth service in distinct layers

---

## Tech Stack

- **Java 17** - Runtime / Language
- **Spring Boot 4.1** - Application Framework
- **Spring Web** - REST Endpoints
- **Spring Security 6.x** - Authentication & Authorization
- **Spring Data JPA** - Database ORM / Persistence
- **Hibernate** - JPA Implementation
- **PostgreSQL** - Relational Database
- **BCrypt** - Password Hashing
- **Lombok** - Code Generation
- **Maven** - Build and Dependency Management
- **Maven Wrapper** - Reproducible Builds

---

## Architecture

The application follows a standard layered architecture:

```text
  Client / Frontend
          │
          ▼
   RestController   (HTTP Layer)
          │
          ▼
     JobService     (Business Logic Layer)
          │
          ▼
      JobRepo       (Database Access Layer)
          │
          ▼
  PostgreSQL DB     (Persistence Layer)
```

With Spring Security added:

```text
  Client Request
       │
       ▼
  SecurityFilterChain  ←── Intercepts & authenticates
       │
       ▼
  DaoAuthenticationProvider  ←── Validates credentials
       │
       ▼
  MyUserDetailsService  ←── Loads user from DB
       │
       ▼
  RestController  →  Service  →  Repo  →  DB
```

### Layer Breakdown
* **RestController**: HTTP Layer that handles incoming requests and formats responses.
* **SecurityConfig**: Configures the security filter chain, auth provider, and password encoder.
* **MyUserDetailsService**: Custom implementation of `UserDetailsService` — loads users from database.
* **UserPrincipal**: Wraps the `User` entity to integrate with Spring Security's `UserDetails` interface.
* **UserService**: Handles user registration with BCrypt password encoding.
* **JobService**: Business Logic Layer that orchestrates core operations.
* **JobRepo**: Database Access Layer powered by Spring Data JPA.
* **PostgreSQL Database**: Persistence Layer for secure data storage.

---

## Entity Model

### User Entity

| Field | Type | Description |
| :--- | :--- | :--- |
| **id** *(PK)* | `int` | Unique identifier |
| **username** | `String` | Login username |
| **password** | `String` | BCrypt-hashed password |

### PostJob Entity

| Field | Type | Description |
| :--- | :--- | :--- |
| **postId** *(PK)* | `int` | Unique identifier (primary key) |
| **postProfile** | `String` | Job title / role name |
| **postDesc** | `String` | Detailed job description |
| **reqExperience** | `int` | Required years of experience |
| **postTechStack** | `List<String>` | List of required technologies/skills |

### Class Relationships

```text
SecurityConfig ──→ DaoAuthenticationProvider ──→ MyUserDetailsService ──→ UserRepo ──→ DB
                                                        │
                                                        ▼
                                                  UserPrincipal (implements UserDetails)
```

---

## API Endpoints

| Method | Endpoint | Auth Required | Description |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | ❌ | Register a new user |
| **GET** | `/jobposts` | ✅ | List all jobs |
| **GET** | `/jobposts/{postId}` | ✅ | Get job by ID |
| **POST** | `/jobposts` | ✅ | Create a new job |
| **PUT** | `/jobposts` | ✅ | Update a job |
| **DELETE** | `/jobposts/{postId}` | ✅ | Delete a job |
| **GET** | `/jobposts/keyword/{keyword}` | ✅ | Search jobs by keyword |
| **GET** | `/load` | ✅ | Load sample data |

### Endpoint Reference

| Method | Endpoint | Auth | Description | Request Body | Response |
|--------|----------|:----:|-------------|--------------|----------|
| POST | /register | ❌ | Register new user | User JSON | User |
| GET | /jobposts | ✅ | Retrieve all job postings | none | List of PostJob |
| GET | /jobposts/{postId} | ✅ | Get a single job by ID | none | PostJob |
| POST | /jobposts | ✅ | Create a new job posting | PostJob JSON | int (postId) |
| PUT | /jobposts | ✅ | Update an existing job posting | PostJob JSON | void |
| DELETE | /jobposts/{postId} | ✅ | Delete a job by ID | none | void |
| GET | /jobposts/keyword/{keyword} | ✅ | Search jobs by keyword | none | List of PostJob |
| GET | /load | ✅ | Load sample job data into database | none | void |

---

## Authentication & Authorization

All endpoints (except `/register`) require **HTTP Basic Authentication**.

### How to Authenticate

```bash
# Register a new user
curl -X POST http://localhost:8083/register \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "username": "shubh", "password": "mypassword"}'

# Access secured endpoints with Basic Auth
curl http://localhost:8083/jobposts \
  -u shubh:mypassword
```

---

## Getting Started

### Prerequisites

- Java 17 (JDK 17+) installed
- PostgreSQL running locally or accessible remotely
- A terminal (Command Prompt, Git Bash, or Powershell)

### Step 1: Clone and Navigate

```bash
git clone https://github.com/shubhdubey1/job-portal-rest-api.git
cd job-portal-rest-api
```

### Step 2: Configure Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### Step 3: Build and Run

On Unix/Mac:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

On Windows:

```cmd
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

The API will start at http://localhost:8083.

### Step 4: Register and Authenticate

```bash
# Register
curl -X POST http://localhost:8083/register \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "username": "shubh", "password": "shubh@123"}'

# Load sample data
curl http://localhost:8083/load -u shubh:shubh@123
```

---

## API Usage

### Create a Job Posting

```bash
curl -X POST http://localhost:8083/jobposts \
  -u shubh:mypassword \
  -H "Content-Type: application/json" \
  -d '{
    "postId": 101,
    "postProfile": "Backend Engineer",
    "postDesc": "Build and maintain server-side applications",
    "reqExperience": 3,
    "postTechStack": ["Java", "Spring Boot", "PostgreSQL"]
  }'
```

### Search for Jobs

```bash
curl http://localhost:8083/jobposts/keyword/Java -u shubh:mypassword
```

### Get All Jobs

```bash
curl http://localhost:8083/jobposts -u shubh:mypassword
```

---

## Project Structure

```text
job-portal-rest-api/
  src/
    main/
      java/com/springcourse/springsecurity1/
        config/
          SecurityConfig.java       # Security filter chain & auth provider
        controller/
          UserController.java       # User registration endpoint
          StudentController.java    # Student endpoints
          HelloController.java      # Health check endpoints
        dao/
          UserRepo.java             # JPA repository for users
        model/
          User.java                 # User entity
          UserPrincipal.java        # Spring Security UserDetails wrapper
          Student.java              # Student entity
        service/
          MyUserDetailsService.java # Loads users from DB for authentication
          UserService.java          # User registration with BCrypt encoding
        SpringSecurity1Application.java  # Application entry point
      resources/
        application.properties      # Configuration
    test/                           # Unit tests
  pom.xml                           # Maven build file
  mvnw / mvnw.cmd                   # Maven Wrapper
  .gitignore                        # Git ignore rules
  README.md                         # This file
```

---

## Author

**Shubh Dubey**
* GitHub: [@shubhdubey1](https://github.com/shubhdubey1)
