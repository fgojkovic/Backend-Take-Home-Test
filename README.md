# Backend Take-Home Test 👋

Hi there ✋! Welcome to the Backend Take-Home Test project.

## 📋 Project Overview

This project was developed as part of a take-home coding test for a backend software engineering position. The task was to create a RESTful API for managing sleep logs, including endpoints for creating sleep logs, retrieving the last night's sleep log, and calculating 30-day averages.

The solution uses Java with Spring Boot, Spring Data JPA for database interactions, and Flyway for database migrations. The project is configured to use H2 Database for testing and PostgreSQL for production.

## 🛠️ Technologies Used

- Java
- Spring Boot
- H2 Database (for testing)
- PostgreSQL (for production)
- Spring Data JPA
- Gradle
- Flyway

## 🚀 Getting Started

### 📋 Installation

1. Clone the repository:
   ```
   git clone https://github.com/fgojkovic/Backend-Take-Home-Test.git
   ```
2. Navigate to the project directory:
   ```
   cd Backend-Take-Home-Test
   ```
3. Build the project:
   ```
   ./gradlew build
   ```
4. Run the application:
   ```
   ./gradlew bootRun
   ```

### 🗄️ Database Setup

The project uses PostgreSQL as the database. You can set up the database using Docker.

#### 🐳 Using Docker

1. Make sure Docker is installed and running.
2. Run the following command to start the PostgreSQL container:
   ```
   docker-compose up -d db
   ```
3. The database will be available at `localhost:5432`.

## 🛠️ Running Migrations

The project uses Flyway for database migrations. To run the migrations, use the following command:

```
./gradlew flywayMigrate
```

## 🔌 API Endpoints

The following API endpoints are available:

- ⬆️ **POST** `/api/sleep/{userId}`: Create a new sleep log for the specified user
- ⬇️ **GET** `/api/sleep/{userId}/last-night`: Get the last night's sleep log for the specified user
- ⬇️ **GET** `/api/sleep/{userId}/averages`: Get the 30-day averages for the specified user

## 🧪 Testing

The project includes unit tests and integration tests. To run the tests, use the following command:

```
./gradlew test
```

## 📜 License

This project is licensed under the MIT License.