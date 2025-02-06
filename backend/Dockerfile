FROM gradle:jdk20 AS builder
WORKDIR /app
VOLUME ["/root/.gradle"]
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY src/main ./src/main
RUN gradle build --refresh-dependencies -x test
COPY src ./src/
RUN gradle build -x test
FROM openjdk:20-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/org.tangut.tangutsite-all.jar /app/tangutsite-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tangutsite-back.jar"]
