# File: Dockerfile

# Use Java 19 JDK base image
FROM eclipse-temurin:19-jdk-alpine

# Set working directory
WORKDIR /var-api

# Copy the built jar into the container
COPY target/var-api-1.0.0.jar var-api.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "var-api.jar"]
