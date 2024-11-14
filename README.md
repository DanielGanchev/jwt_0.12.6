# JWT Implementation

This project is a Spring Boot application that implements JWT (JSON Web Token) based authentication 0.12.6 without Spring Security with Mail system for Reset Password and Email Verification.



## Prerequisites

- Java 21
- Gradle
- MySQL
- Docker (for MailHog)

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/jwt-implementation.git
    cd jwt-implementation
    ```

2. Set up the MySQL database:
    ```sql
    CREATE DATABASE jwt_db;
    ```

3. Update the database configuration in `src/main/resources/application.yml`:
    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/jwt_db?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true
        username: your_mysql_username
        password: your_mysql_password
        driver-class-name: com.mysql.cj.jdbc.Driver
    ```

4. Add the JWT secret to `src/main/resources/application.yml`:
    ```yaml
    jwt:
      secret: your_secret_key_here
    ```
5. Set up MailHog using Docker:
    ```bash
    docker-compose up -d
    ```
## Configuration

Ensure that the following environment variables are set:
- `MYSQL_USER`: Your MySQL username
- `MYSQL_PASSWORD`: Your MySQL password
- `JWT_SECRET`: Some Jwt Generated Secret

## Running the Application

1. Build the project:
    ```bash
    ./gradlew build
    ```

2. Run the application:
    ```bash
    ./gradlew bootRun
    ```

## API Endpoints

### Authentication

- **Register**: `POST /api/v1/authentication/register`
    - Request Body:
        ```json
        {
          "email": "user@example.com",
          "password": "password"
        }
        ```
    - Response:
        ```json
        {
          "token": "jwt_token",
          "message": "User registered"
        }
        ```

- **Login**: `POST /api/v1/authentication/login`
    - Request Body:
        ```json
        {
          "email": "user@example.com",
          "password": "password"
        }
        ```
    - Response:
        ```json
        {
          "token": "jwt_token",
          "message": "User logged in"
        }
        ```

### Email Verification

- **Send Email Verification Token**: `GET /api/v1/authentication/send-email-verification-token`
- **Validate Email Verification Token**: `PUT /api/v1/authentication/validate-email-verfication-token`
    - Request Params:
        - `token`: The verification token

### Password Reset

- **Send Password Reset Token**: `PUT /api/v1/authentication/send-password-reset-token`
    - Request Params:
        - `email`: The user's email
- **Reset Password**: `PUT /api/v1/authentication/reset-password`
    - Request Params:
        - `email`: The user's email
        - `newPassword`: The new password
        - `token`: The reset token

## License


