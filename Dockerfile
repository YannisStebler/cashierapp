# Stage 1: Build
FROM openjdk:23 as build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Ensure Maven is installed and use it to build the project
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:23

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
