# Job Portal REST API
 
![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=java&logoColor=white)
![Spring Boot 4.1](https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=flat-square&logo=oracle&logoColor=white)
![JPA](https://img.shields.io/badge/JPA/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white)
 
A RESTful API for managing job postings built with **Spring Boot**, **Spring Data JPA**, and **Oracle Database**.
 
---
 
## Table of Contents
 
- [Overview](#overview)
- [Skills Showcased](#skills-showcased)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Entity Model](#entity-model)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [API Usage](#api-usage)
- [Project Structure](#project-structure)
- [Author](#author)
 
---
 
## Overview
 
This backend service powers a job portal platform by exposing RESTful endpoints to perform CRUD operations on job postings. Each job posting includes a title, description, required experience, and tech stack. The API also supports keyword-based search across job profiles and descriptions.
 
The project follows a clean layered architecture: **Controller -> Service -> Repository -> Database**.
 
---
 
## Skills Showcased
 
| Skill | Implementation Details |
|-------|----------------------|
| REST API Design | Full CRUD with proper HTTP methods (GET, POST, PUT, DELETE) |
| Spring Boot 4.1 | Auto-configuration, embedded server, starter dependencies |
| Spring Data JPA | Repository abstraction with JpaRepository and custom query derivation |
| Oracle Database | Integration with ojdbc11 driver and Hibernate DDL auto-generation |
| Layered Architecture | Clean separation: Controller, Service, Repository, Entity |
| Lombok | Reduced boilerplate with @Data, @AllArgsConstructor, @NoArgsConstructor |
| Maven Wrapper | Build without local Maven installation using mvnw |
| Environment Config | Database credentials externalized via environment variables |
 
---
 
## Tech Stack
 
- **Java 17** - Runtime / Language
- **Spring Boot 4.1** - Application Framework
- **Spring Web** - REST Endpoints
- **Spring Data JPA** - Database ORM / Persistence
- **Hibernate** - JPA Implementation
- **Oracle DB** - Relational Database
- **Lombok** - Code Generation
- **Maven 3.9.16** - Build and Dependency Management
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
  Oracle Database   (Persistence Layer)
```

### Layer Breakdown
* **RestController**: HTTP Layer that handles incoming requests and formats responses.
* **JobService**: Business Logic Layer that orchestrates core operations.
* **JobRepo**: Database Access Layer powered by Spring Data JPA.
* **Oracle Database**: Persistence Layer for secure data storage.

 
### Layer Responsibilities
 
| Layer | Responsibility |
|-------|----------------|
| **RestController** | Handles HTTP requests and routes to service methods |
| **JobService** | Business logic and data orchestration |
| **JobRepo** | Database access via Spring Data JPA |
| **PostJob** (Entity) | JPA entity mapped to database table |
 
---
 
## Entity Model
 
The core entity is **PostJob**, which maps to the job postings table:

| Field | Type | Description |
| :--- | :--- | :--- |
| **postId** *(PK)* | `int` | Unique identifier (primary key) |
| **postProfile** | `String` | Job title / role name |
| **postDesc** | `String` | Detailed job description |
| **reqExperience** | `int` | Required years of experience |
| **postTechStack** | `List<String>` | List of required technologies/skills |

### Class Relationships

```text
RestController ---> JobService ---> JobRepo ---> Oracle DB

|                  |               |
v                  v               v
PostJob            PostJob         PostJob
(returns)          (uses)          (uses)
```

---

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/jobposts` | List all jobs |
| **GET** | `/jobposts/{postId}` | Get job by ID |
| **POST** | `/jobposts` | Create a new job |
| **PUT** | `/jobposts` | Update a job |
| **DELETE** | `/jobposts/{postId}` | Delete a job |
| **GET** | `/jobposts/keyword/{keyword}` | Search jobs by keyword |
| **GET** | `/load` | Load sample data |

 
### Endpoint Reference
 
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | /jobposts | Retrieve all job postings | none | List of PostJob |
| GET | /jobposts/{postId} | Get a single job by ID | none | PostJob |
| POST | /jobposts | Create a new job posting | PostJob JSON | int (postId) |
| PUT | /jobposts | Update an existing job posting | PostJob JSON | void |
| DELETE | /jobposts/{postId} | Delete a job by ID | none | void |
| GET | /jobposts/keyword/{keyword} | Search jobs by keyword | none | List of PostJob |
| GET | /load | Load sample job data into database | none | void |
 
---
 
## Getting Started
 
### Prerequisites
 
- Java 17 (JDK 17+) installed
- Oracle Database running locally or accessible remotely
- A terminal (Command Prompt, Git Bash, or Powershell)
 
### Step 1: Clone and Navigate
 
```bash
git clone https://github.com/shubhdubey1/job-portal-rest-api.git
cd job-portal-rest-api
```

### Step 2: Configure Database
Set these environment variables:

```bash
export DB_URL=jdbc:oracle:thin:@localhost:1521:XE
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

Or edit `src/main/resources/application.properties` directly.

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

### Step 4: Load Sample Data

```bash
curl http://localhost:8083/load
```

---

## API Usage

### Create a Job Posting

```bash
curl -X POST http://localhost:8083/jobposts \
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
curl http://localhost:8083/jobposts/keyword/Java
```

### Get All Jobs

```bash
curl http://localhost:8083/jobposts
```

---

## Project Structure

```text
job-portal-rest-api/
  src/
    main/
      java/com/springcourse/springbootrest/
        Model/           - Entity classes
        Repository/      - JPA repositories
        Service/         - Business logic layer
        RestController.java   - REST API endpoints
        SpringBootRestApplication.java  - Application entry point
      resources/
        application.properties  - Configuration
    test/                - Unit tests
  pom.xml                - Maven build file
  mvnw / mvnw.cmd        - Maven Wrapper (build without installing Maven)
  .gitignore             - Git ignore rules
  README.md              - This file
```

---

## Author
**Shubh Dubey**
* GitHub: [@shubhdubey1](https://github.com/shubhdubey1)
