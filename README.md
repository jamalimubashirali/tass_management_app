# Task Management Application

A Spring Boot-based task management application with secure REST APIs.

## Technologies Used

- Java 21
- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication (JJWT 0.12.6)

## Prerequisites

Before you begin, ensure you have the following installed:

- **JDK 21 or later**
- **Maven 3.9.9 or later** (a Maven Wrapper is included)
- **PostgreSQL**

## Getting Started

### Clone the Repository

Open a terminal and run the following command:

```bash
git clone https://github.com/jamalimubashirali/task_management_app
cd task_management_app
```


### Configure the Database

Create a new PostgreSQL database using your preferred method (e.g., `psql`, pgAdmin, etc.):

```sql
CREATE DATABASE taskdb;
```

Then update the database configuration in `src/main/resources/application.properties` as follows:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Replace `your_username` and `your_password` with your PostgreSQL credentials.

### Build and Run the Application

The project includes the Maven Wrapper, so you don't need to install Maven separately.

#### Using Maven Wrapper

Run the following commands:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

#### Using Installed Maven

If you already have Maven installed:

```bash
mvn clean install
mvn spring-boot:run
```

Once running, the application will be available at:

```
http://localhost:8080
```

## Features

- ✅ JWT-based authentication
- 🔐 Protected REST endpoints
- 🗂️ Task creation, update, deletion, and listing
- 💾 PostgreSQL persistence using Spring Data JPA
- 🔐 Role-based access control (optional enhancement)

## Project Structure

```
task_management_app/
├── .mvn/                     # Maven wrapper files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.taskmanagementapp/  # Main package
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       ├── controller/
│   │   │       └── config/
│   │   │       └── security/
│   │   │       └── TaskManagementApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql        # Optional seed data
│   └── test/
│       └── java/
└── mvnw                      # Unix executable
└── mvnw.cmd                  # Windows executable
└── pom.xml                   # Maven build configuration
└── README.md                 # This file
```

## Security

This application uses **JWT tokens** for stateless authentication. Users must log in to obtain a token and then use it in the `Authorization` header for all protected requests.

### How It Works

1. A user logs in via `/api/v1/login`.
2. The server returns a JWT token.
3. For all subsequent requests, include the token like this:

   ```
   Authorization: Bearer <your-token>
   ```

## API Endpoints

All endpoints return JSON responses.

### Authentication Endpoints

| Method | Endpoint             | Description                |
|--------|----------------------|----------------------------|
| POST   | `/api/v1/register` | Register a new user        |
| POST   | `/api/v1/login`    | Log in and get JWT token   |

### Task Management Endpoints (Protected)

| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| GET    | `/api/v1/tasks`        | Get list of all tasks                |
| POST   | `/api/v1/create-task`        | Create a new task                    |
| GET    | `/api/v1/tasks/{task_id}`   | Get task by ID                       |
| PATCH    | `/api/v1/tasks/update_task/{task_id}`   | Update an existing task              |
| DELETE | `/api/v1/tasks/delete-task/{task_id}`   | Delete a task                        |

## Example Requests

### Login Request

```http
POST /api/v1/login HTTP/1.1
Content-Type: application/json

{
  "username": "admin",
  "password": "password"
}
```

### Expected Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx"
}
```

### Accessing Protected Resource

```http
GET /api/v1/tasks HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx
```

## Database Schema

There are two core tables used in this application:

### Table: `users`

| Column        | Type         | Description               |
|---------------|--------------|---------------------------|
| id            | BIGINT       | Primary Key               |
| username      | VARCHAR(50)  | Unique username           |
| email         | VARCHAR(100) | Unique email address      |
| password      | VARCHAR(255) | Hashed password           |

### Table: `tasks`

| Column        | Type         | Description               |
|---------------|--------------|---------------------------|
| id            | BIGINT       | Primary Key               |
| title         | VARCHAR(100) | Title of the task         |
| description   | TEXT         | Optional task description |
| completed     | BOOLEAN      | Whether the task is done  |
| user_id       | BIGINT       | Foreign key to `users`    |

## Testing

To run unit and integration tests:

```bash
./mvnw test
```

## Build for Production

To build a production-ready `.jar` file:

```bash
./mvnw clean package
```

The compiled JAR will be located in the `target/` directory.

## Deployment

You can run the built JAR file on any system with Java installed:

```bash
java -jar target/task_management_app-0.0.1-SNAPSHOT.jar
```

For deployment on servers or cloud platforms, consider:

- Heroku
- AWS EC2 / ECS
- Docker containerization
- Kubernetes orchestration

## Contributing

Here's how to contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add cool feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request


