#syntax=docker/dockerfile:1

# --- Build Stage ---
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven project configuration
COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline

# Copy source
COPY src ./src/

# Build application
RUN mvn package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Create non-root user
RUN useradd -m appuser
USER appuser

# Copy JAR
COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80.0", "-jar", "/app/app.jar"]