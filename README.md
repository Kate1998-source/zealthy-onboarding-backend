# Zealthy Onboarding Backend

Spring Boot REST API for custom onboarding flow - Zealthy Full Stack Engineering Exercise.

## Features
- User registration with email validation
- MySQL database integration
- Admin configuration management
- CORS enabled for frontend
- RESTful API endpoints

## Tech Stack
- Spring Boot 3.3.5
- Java 17
- MySQL 8.0
- JPA/Hibernate
- Maven

## API Endpoints
- POST /api/users/register-complete - Complete user registration
- GET /api/users/email/{email} - Check email availability
- GET /api/users - Get all users
- GET/PUT /api/admin/config - Admin configuration

## Setup
```bash
mvn clean install
mvn spring-boot:run