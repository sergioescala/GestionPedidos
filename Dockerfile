# Usar la imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim
# Crear directorio de trabajo
WORKDIR /app
# Copiar el JAR generado al contenedor
COPY target/orderservice-0.0.1-SNAPSHOT.jar orderservice.jar
# Exponer el puerto 8080
EXPOSE 8080
# Comando de inicio
ENTRYPOINT ["java", "-jar", "orderservice.jar"]
