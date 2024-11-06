# Usa una imagen base adecuada para Java
FROM openjdk:17-jdk-slim

# Crea el directorio de la aplicación
WORKDIR /app

# Copia el archivo JAR
COPY target/frontend-0.0.1-SNAPSHOT.jar /app/frontend.jar

# Exponer el puerto que usará el servicio
EXPOSE 8080

# Ejecuta el JAR de la aplicación
CMD ["java", "-jar", "frontend.jar"]

