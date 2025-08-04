# Spring Boot Authentication & Authorization System

A comprehensive Spring Boot REST API for authentication and authorization with JWT tokens, role-based access control, and password recovery functionality.

## Features

- **User Registration & Login**: Complete user management system
- **JWT Authentication**: Secure token-based authentication
- **Refresh Tokens**: Long-lived tokens for seamless user experience
- **Role-Based Authorization**: Admin and User roles with different permissions
- **Password Recovery**: Email-based password reset functionality
- **Swagger Documentation**: Interactive API documentation
- **H2 Database**: In-memory database for development (easily configurable for production)

## Technologies Used

- **Spring Boot 3.3.0**
- **Spring Security 6**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **H2 Database** (development)
- **MySQL** (production ready)
- **Swagger/OpenAPI 3**
- **Maven**
- **Java 17**

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone the repository**
   ```bash
   gh repo clone YuriTheCoder/loginsystem2
   cd loginsystem2
   ```

2. **Configure application properties** (optional)
   - Update `src/main/resources/application.yml` for custom configurations
   - Set up email credentials for password recovery feature

3. **Build and run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8081/api`
   - Swagger UI: `http://localhost:8081/api/swagger-ui.html`
   - H2 Console: `http://localhost:8081/api/h2-console`

## Default Users

The application creates default users on startup:

| Username | Password | Role        | Email              |
|----------|----------|-------------|--------------------|
| admin    | admin123 | ADMIN, USER | admin@example.com  |
| user     | user123  | USER        | user@example.com   |

## API Endpoints

### Authentication Endpoints

| Method | Endpoint                    | Description                |
|--------|-----------------------------|----------------------------|
| POST   | `/auth/signup`              | Register a new user        |
| POST   | `/auth/signin`              | Login user                 |
| POST   | `/auth/refreshtoken`        | Refresh access token       |
| POST   | `/auth/signout`             | Logout user                |
| POST   | `/auth/forgot-password`     | Request password reset     |
| POST   | `/auth/reset-password`      | Reset password with token  |

### User Management Endpoints

| Method | Endpoint        | Description                    | Required Role |
|--------|-----------------|--------------------------------|---------------|
| GET    | `/users/me`     | Get current user info          | USER, ADMIN   |
| PUT    | `/users/me`     | Update current user            | USER, ADMIN   |
| GET    | `/users`        | Get all users                  | ADMIN         |
| GET    | `/users/{id}`   | Get user by ID                 | ADMIN         |
| PUT    | `/users/{id}`   | Update user by ID              | ADMIN         |
| DELETE | `/users/{id}`   | Delete user                    | ADMIN         |

### Test Endpoints

| Method | Endpoint        | Description           | Required Role |
|--------|-----------------|-----------------------|---------------|
| GET    | `/test/public`  | Public access         | None          |
| GET    | `/test/user`    | User access           | USER, ADMIN   |
| GET    | `/test/admin`   | Admin access          | ADMIN         |

## Usage Examples

### 1. User Registration

```bash
curl -X POST http://localhost:8081/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 2. User Login

```bash
curl -X POST http://localhost:8081/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "newuser",
    "password": "password123"
  }'
```

### 3. Access Protected Endpoint

```bash
curl -X GET http://localhost:8081/api/users/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 4. Refresh Token

```bash
curl -X POST http://localhost:8081/api/auth/refreshtoken \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'
```

### 5. Password Recovery

```bash
# Request password reset
curl -X POST "http://localhost:8081/api/auth/forgot-password?email=user@example.com"

# Reset password with token
curl -X POST http://localhost:8081/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "RESET_TOKEN_FROM_EMAIL",
    "newPassword": "newpassword123"
  }'
```

## Configuration

### JWT Configuration

```yaml
jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 86400000      # 24 hours
  refresh-expiration: 2592000000  # 30 days
```

### Database Configuration

For production, update the database configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/authdb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

### Email Configuration

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

## Security Features

- **Password Encryption**: BCrypt hashing
- **JWT Security**: HMAC-SHA512 signed tokens
- **CORS Support**: Configurable cross-origin requests
- **Method-Level Security**: Role-based access control
- **Input Validation**: Request validation with detailed error messages
- **Exception Handling**: Global exception handling with proper HTTP status codes

## API Documentation

Access the interactive Swagger documentation at:
- **Swagger UI**: `http://localhost:8081/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/api/api-docs`

## Testing

Run the tests with:

```bash
mvn test
```

## Production Deployment

1. **Update application.yml** for production environment
2. **Configure external database** (MySQL, PostgreSQL)
3. **Set environment variables** for sensitive data
4. **Configure email service** for password recovery
5. **Set up HTTPS** for secure communication
6. **Configure logging** for production monitoring

## Environment Variables

Set these environment variables for production:

```bash
JWT_SECRET=your-production-jwt-secret-key
DB_USERNAME=your-database-username
DB_PASSWORD=your-database-password
MAIL_USERNAME=your-email-username
MAIL_PASSWORD=your-email-app-password
```
