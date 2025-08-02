# ======================
# Stage 1: Build with Maven
# ======================
FROM maven:3.9.6-eclipse-temurin-17 AS build
# NOTE: Java 19 variant is not available, use Java 17 or switch base image.

WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# ======================
# Stage 2: Run with JDK
# ======================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /build/target/var-api-1.0.0.jar var-api.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "var-api.jar"]
