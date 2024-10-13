
# Spring Boot Application with Java 21 Virtual Threads

This project demonstrates how to use **Java 21's virtual threads** in a Spring Boot application. It includes two approaches for managing virtual threads:
1. **Using Spring Boot's built-in support** (`spring.threads.virtual.enabled=true`).
2. **Manually configuring virtual threads** with `ExecutorService`.

## Features
- **Virtual Threads**: High scalability and efficient resource usage with Java 21 virtual threads.
- **Spring Boot Integration**: Simplified configuration for virtual threads in Spring Boot.
- **JPA Integration**: Full support for database operations using Spring Data JPA and H2 in-memory database.
- **Concurrent Processing**: Async methods and HTTP request handling using virtual threads.

---

## Getting Started

### Prerequisites

- **Java 21** or later (required to use virtual threads).
- **Maven** or **Gradle** for building the project.
- **Spring Boot 3.3.x** or later.
  
### Setup

1. Clone the repository:
    \`\`\`bash
    git clone https://github.com/hamidkhanjani/Virtual-Theads.git
    \`\`\`

2. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

3. Build the project using Gradle:
    \`\`\`bash
    ./gradlew clean build
    \`\`\`

### Running the Application

Run the Spring Boot application using your preferred method:


- **Gradle**:
    \`\`\`bash
    ./gradlew bootRun
    \`\`\`

Once the application is running, you can access the API at `http://localhost:8080`.

---

## Configuration

### Enabling Virtual Threads

You can enable Spring Boot's built-in virtual thread support by adding the following property in `application.properties`:

\`\`\`properties
spring.threads.virtual.enabled=true
\`\`\`

This will configure Spring to use virtual threads for handling HTTP requests, asynchronous tasks, and scheduled tasks.

### Manual Virtual Thread Management

If you need more control over virtual threads, you can manually configure an \`ExecutorService\` that uses virtual threads:

\`\`\`java
private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
\`\`\`

This can be used to handle concurrency in specific services or parts of the application.

---

## Endpoints

### User API

This project uses a simple **User** entity with the following CRUD endpoints:

- **GET** `/users`: Retrieve all users.
- **POST** `/users`: Create a new user.
  - Request body example:
    \`\`\`json
    {
        "name": "John Doe",
        "email": "john.doe@example.com"
    }
    \`\`\`

- **GET** `/users/{id}`: Retrieve a user by their ID.
- **DELETE** `/users/{id}`: Delete a user by their ID.

---

## Running Tests

The project includes both **unit tests** and **integration tests**.

- **Unit Tests**: Test the service layer logic using mocked repositories.
- **Integration Tests**: Test the full Spring Boot stack, including the API and JPA interactions using an H2 in-memory database.

### Running Tests

To run all the tests:


- **Gradle**:
    \`\`\`bash
    ./gradlew test
    \`\`\`

---

## Technologies Used

- **Java 21**: Leveraging virtual threads from Project Loom.
- **Spring Boot 3.3.x**: Core framework for building REST APIs.
- **Spring Data JPA**: For handling database interactions.
- **H2 Database**: In-memory database for testing.
- **JUnit 5**: For unit and integration testing.
- **Mockito**: For mocking in unit tests.

---

## Advanced Topics

### Choosing Between Spring Boot's Built-in Virtual Threads and Manual Configuration

You can manage virtual threads in two ways:

1. **Spring Boot's Built-in Virtual Threads**:
   - Enable with \`spring.threads.virtual.enabled=true\` in \`application.properties\`.
   - Ideal for most scenarios where you want Spring to manage concurrency across the entire application.

2. **Manually Configuring Virtual Threads**:
   - Use \`ExecutorService\` to create virtual threads only where needed (e.g., for I/O-bound tasks).
   - Useful if you need more fine-grained control over concurrency.

Refer to the code examples in the source files for more details.

---

## Further Reading

- [Java 21 Official Documentation](https://docs.oracle.com/en/java/javase/21/)
- [Spring Boot Virtual Threads Documentation](https://spring.io/blog)
- [Project Loom Overview](https://wiki.openjdk.org/display/loom/Main)

---

## License

This project is licensed under the MIT License.
