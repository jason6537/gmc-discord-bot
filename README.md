## Running with Docker

This project provides a multi-stage Dockerfile and a Docker Compose setup for building and running the Java application in a containerized environment.

### Project-Specific Docker Requirements
- **Base Image:** Uses `eclipse-temurin:17-jdk` for building and `eclipse-temurin:17-jre` for running (Java 17 required).
- **Build Tool:** Maven Wrapper (`mvnw`) is used for building the project inside the container.
- **Non-root User:** The runtime container runs as a non-root user (`appuser`) for improved security.

### Environment Variables
- The Dockerfile sets `JAVA_OPTS` to `-XX:MaxRAMPercentage=80.0` for JVM memory management.
- No required environment variables are specified by default. If your application needs environment variables, you can add them to a `.env` file and uncomment the `env_file` line in `docker-compose.yml`.

### Build and Run Instructions
1. **Build and start the application:**
   ```sh
   docker compose up --build
   ```
   This will build the application using Maven and run it in a container.

2. **Stopping the application:**
   ```sh
   docker compose down
   ```

### Special Configuration
- **No Ports Exposed:** By default, the application does not expose any ports. If your application requires external access, update the Docker Compose file to expose the necessary ports.
- **No External Services:** The current setup does not include any databases or other services. If needed, add them to `docker-compose.yml`.
- **Persistent Data:** No volumes are configured, as the application does not require persistent storage by default.

### Summary
- **Java Version:** 17 (Eclipse Temurin)
- **Build Tool:** Maven Wrapper
- **How to Run:** `docker compose up --build`
- **Ports:** None exposed by default
- **Environment:** Optional `.env` file for custom variables

Refer to the Dockerfile and `docker-compose.yml` for further customization as your project evolves.
