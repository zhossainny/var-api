# ======================
# Stage 1: Build with Maven (Java 17)
# ======================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# ======================
# Stage 2: Run with JDK 17
# ======================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /build/target/*.jar var-api.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "var-api.jar"]

