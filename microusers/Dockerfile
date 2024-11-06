# Usa una imagen base adecuada para Java
FROM openjdk:17-jdk-slim

# Crea el directorio de la aplicación
WORKDIR /app

# Copia el archivo JAR
COPY target/microusers-0.0.1-SNAPSHOT.jar /app/microusers.jar

# Exponer el puerto que usará el servicio
EXPOSE 8081

# Ejecuta el JAR de la aplicación
CMD ["java", "-jar", "microusers.jar"]

