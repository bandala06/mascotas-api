FROM ubuntu:latest
LABEL authors="Bandala"

ENTRYPOINT ["top", "-b"]

# Etapa 1: Construcción (Descarga Java y Maven)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copiamos los archivos de tu proyecto
COPY pom.xml .
COPY src ./src
# Compilamos el código saltando las pruebas para que sea más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Una versión más ligera de Java solo para correr la app)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el archivo .jar que se generó en la etapa 1
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto 8080
EXPOSE 8080
# Comando final para encender Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]