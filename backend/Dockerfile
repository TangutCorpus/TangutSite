FROM gradle:jdk20 AS builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
RUN chmod +x gradlew
COPY . .
RUN ./gradlew build -x test
FROM amazoncorretto:20
WORKDIR /app
COPY --from=builder /app/build/libs/org.tangut.tangutsite-all.jar /app/tangutsite-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tangutsite-back.jar"]