# Stage 1: Build using Maven Wrapper
FROM maven:3.9.6-eclipse-temurin-19 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the built jar
FROM eclipse-temurin:19-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/var-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
