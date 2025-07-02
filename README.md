# Learning Spring

This project is a simple Spring Boot application for learning and experimenting with Spring technologies, including:

- Spring Boot
- Spring Security with JWT authentication
- RESTful API endpoints for user management

## Features

- User registration and authentication
- JWT-based stateless security
- Basic CRUD operations for users

## Requirements

- Java 17+
- Maven

## Running the Project

1. Clone the repository.
2. Navigate to the `learning-spring` directory.
3. Build and run the application:

   ```sh
   mvn spring-boot:run
   ```

4. The API will be available at `http://localhost:8080`.

## Endpoints

- `POST /user` — Register a new user
- `POST /user/login` — Authenticate and receive a JWT token
- `GET /user?email=...` — Get user by email (requires authentication)
- `DELETE /user/{email}` — Delete user by email (requires authentication)

## License

This project is for educational purposes.
