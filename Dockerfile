# Verwende ein OpenJDK-Image für Java
FROM openjdk:23

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere das gebaute JAR-File in den Container
COPY target/*.jar app.jar

# Exponiere Port 8080 für den Zugriff auf die Anwendung
EXPOSE 8080

# Starte die Spring Boot App
CMD ["java", "-jar", "app.jar"]
