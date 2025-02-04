# Stage 1: Builder
FROM gradle:jdk20 AS builder
WORKDIR /app
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
RUN ./gradlew dependencies --no-daemon
COPY . .
RUN ./gradlew build -x test --no-daemon
FROM eclipse-temurin:20-jre
WORKDIR /app
COPY --from=builder /app/build/libs/org.tangut.tangutsite-all.jar /app/tangutsite-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tangutsite-back.jar"]
