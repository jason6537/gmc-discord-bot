# syntax=docker/dockerfile:1

# --- Build Stage ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Maven wrapper and config first for caching
COPY --link pom.xml mvnw ./
COPY --link .mvn .mvn/

# Make sure mvnw is executable and download dependencies (cacheable)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY --link src ./src/

# Build the application (skip tests for faster CI/CD)
RUN ./mvnw package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Create a non-root user for security
RUN useradd -m appuser
USER appuser

# Copy built jar from build stage
COPY --link --from=build /app/target/*.jar /app/app.jar

# JVM container flags for memory/resource management
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0"

# Use exec form for proper signal handling
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80.0", "-jar", "/app/app.jar"]
