# Zealthy Onboarding Backend

Spring Boot REST API for custom onboarding flow - Zealthy Full Stack Engineering Exercise.

## 🚀 Live Demo

**Backend API:** [Will be updated after Railway deployment]  
**Frontend Repository:** [Will be updated with frontend repo URL]

## ✨ API Endpoints

### User Management
- `POST /api/users/register-complete` - Complete user registration
- `GET /api/users/email/{email}` - Check email availability  
- `GET /api/users` - Get all users for data table
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/test` - Health check

### Admin Configuration
- `GET /api/admin/config` - Get component configuration
- `PUT /api/admin/config` - Update component configuration

### Data Table
- `GET /api/data/users` - Alternative endpoint for user data

## 🛠 Tech Stack

- **Framework**: Spring Boot 3.1
- **Java**: JDK 17
- **Database**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **Validation**: Bean Validation
- **Deployment**: Railway
- **Monitoring**: Spring Actuator

## 🏗 Project Structure

```
src/main/java/com/zealthy/onboarding/
├── controller/
│   ├── UserController.java      # User API endpoints
│   ├── DataController.java      # Data table endpoints
│   └── AdminController.java     # Admin configuration
├── service/
│   ├── UserService.java         # Business logic
│   └── AdminService.java        # Admin logic
├── repository/
│   ├── UserRepository.java      # Data access
│   └── AdminConfigRepository.java
├── entity/
│   ├── User.java               # User entity
│   └── AdminConfig.java        # Config entity
├── dto/
│   ├── UserResponse.java        # API responses
│   ├── CompleteUserRequest.java # Registration request
│   └── UserRegistrationRequest.java
├── config/
│   └── CorsConfig.java         # CORS configuration
└── OnboardingApplication.java   # Main application
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Local Development
```bash
# Clone repository
git clone [your-repository-url]
cd zealthy-onboarding-backend

# Configure database
# Update application.properties with your MySQL credentials

# Run application
mvn spring-boot:run

# Test API
curl http://localhost:8080/api/users/test
```

## 🔧 Configuration

### application.properties
```properties
# Database (update with your credentials)
spring.datasource.url=jdbc:mysql://localhost:3306/zealthy_onboarding
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server
server.port=8080
```

### Environment Variables (Production)
```env
DATABASE_URL=mysql://username:password@host:port/database
PORT=8080
FRONTEND_URL=https://your-frontend-url.vercel.app
```

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    about_me TEXT,
    street_address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    zip VARCHAR(20),
    birthdate DATE,
    current_step INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Admin Config Table
```sql
CREATE TABLE admin_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    page_number INT NOT NULL,
    component_type VARCHAR(50) NOT NULL,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔒 Security Features

- **Email Validation**: Unique email enforcement
- **CORS Configuration**: Production-ready cross-origin settings
- **Input Validation**: Bean validation on all endpoints
- **Error Handling**: Comprehensive exception management
- **SQL Injection Protection**: JPA/Hibernate parameterized queries

## 🌐 CORS Configuration

**Allowed Origins:**
- `http://localhost:3000` (development)
- `https://*.vercel.app` (Vercel deployments)
- `https://*.netlify.app` (Netlify deployments)
- Environment-specific frontend URLs

## 🚀 Production Deployment

### Railway Deployment
1. Connect GitHub repository to Railway
2. Add MySQL database service
3. Set environment variables:
   - `DATABASE_URL` (from Railway MySQL)
   - `FRONTEND_URL` (your deployed frontend)
4. Deploy automatically on push

### Docker (Alternative)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📈 Monitoring & Health

- **Health Check**: `/api/users/test`
- **Actuator**: `/actuator/health`
- **Database**: Connection pooling with HikariCP
- **Logging**: Structured logging for production

## 🧪 Testing

```bash
# Run tests
mvn test

# Integration tests
mvn verify

# Test coverage
mvn jacoco:report
```

## 📋 API Examples

### Complete User Registration
```bash
curl -X POST http://localhost:8080/api/users/register-complete \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "aboutMe": "Software developer",
    "streetAddress": "123 Main St",
    "city": "San Francisco",
    "state": "CA",
    "zip": "94105",
    "birthdate": "1990-01-01"
  }'
```

### Check Email Availability
```bash
curl http://localhost:8080/api/users/email/user@example.com
# Returns 200 if exists, 404 if available
```

### Get All Users
```bash
curl http://localhost:8080/api/users
```

## 🐛 Troubleshooting

**Database Connection Issues:**
- Verify MySQL is running
- Check connection string format
- Ensure database exists

**CORS Errors:**
- Update allowed origins in CorsConfig
- Check frontend URL configuration
- Verify preflight requests

**Port Conflicts:**
- Change port in application.properties
- Kill processes using port 8080

## 📦 Production Features

- **Connection Pooling**: Optimized database connections
- **Error Responses**: Standardized API error format
- **Request Logging**: Comprehensive request/response logging
- **Graceful Shutdown**: Clean application termination
- **Health Monitoring**: Production-ready health endpoints

