# Usa la imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/microservice-auth-0.0.1-SNAPSHOT.jar /app/microservice-autenticacion.jar

# Define el comando que se ejecutará cuando el contenedor se inicie
CMD ["java", "-jar", "/app/microservice-autenticacion.jar"]