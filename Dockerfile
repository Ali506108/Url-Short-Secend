# Stage 1: сборка
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# копируем Gradle wrapper и настройки сборки
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle

# копируем исходники и собираем fat-jar
COPY src ./src
RUN chmod +x gradlew \
 && ./gradlew clean bootJar -x test

# Stage 2: лёгкий рантайм
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# копируем собранный артефакт
COPY --from=builder /app/build/libs/*.jar app.jar

# порт приложения
EXPOSE 8080

# опции JVM и запуск
ENTRYPOINT ["java", "-Xms512m", "-Xmx1g", "-jar", "app.jar"]
