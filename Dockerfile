FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/ur app.jar

# Если сервис слушает определенный порт (например, 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1g", "-jar", "app.jar"]
