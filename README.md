# 🚀 Buildbridge

**Buildbridge**: Connecting Professionals, Simplifying Networking 🌐

## 📖 Description

Buildbridge is a Java Spring Boot (Maven) backend project designed to create a professional networking platform. The application enables users to connect with colleagues, share projects, and collaborate on ideas. With a robust backend infrastructure, Buildbridge provides a seamless experience for professionals to focus on building meaningful relationships and advancing their careers.

The platform uses a modular architecture with separate packages for user management, project sharing, and connection management, allowing scalability, flexibility, and maintainability. By leveraging Spring Boot and Maven, Buildbridge ensures a robust, efficient, and secure environment for its users.

## ✨ Features

* **User Management**: Create and manage user profiles, including profile pictures, bio, and contact info
* **Project Sharing**: Share projects and collaborate, with commenting support
* **Connection Management**: Send/receive connection requests, manage connections
* **Post Management**: Create and manage posts (text, images, videos)
* **Comment Management**: Comment on posts/projects with threaded conversations
* **Like Management**: Like posts/projects and track likes
* **Security**: Authentication, authorization, and encryption using Spring Security & JWT
* **API Support**: RESTful APIs for user, project, and connection management

## 🧰 Tech Stack

| Category | Technology                                       |
| -------- | ------------------------------------------------ |
| Frontend | Backend-only project                             |
| Backend  | Java Spring Boot, Maven                          |
| Database | Spring Data JPA (supports MySQL, H2, PostgreSQL) |
| Security | Spring Security, JWT                             |
| APIs     | RESTful APIs, JSON                               |

## 📁 Project Structure

* `com.wasim.buildbridge` - Main package with application configuration and entry point
* `com.wasim.buildbridge.config` - Security & database configuration classes
* `com.wasim.buildbridge.repository` - User, project, and connection repositories
* `com.wasim.buildbridge.service` - User, project, and connection services
* `com.wasim.buildbridge.jwt` - JWT handling classes

## ⚙️ Getting Started

### Clone & Run

```bash
git clone https://github.com/your-repo/buildbridge.git
cd buildbridge
mvn clean package
mvn spring-boot:run
```

* Application starts on port `8080`. Access via `http://localhost:8080`.

### Running Tests

```bash
cd buildbridge
mvn test
```

* Tests display results including failures or errors.

## 📦 API Reference

* `POST /users` : Create a new user
* `GET /users/{username}` : Get a user by username
* `POST /projects` : Create a new project
* `GET /projects/{projectId}` : Get project by ID

> API documentation will be added soon (Swagger/Postman collection).

## 👤 Author

Wasim
