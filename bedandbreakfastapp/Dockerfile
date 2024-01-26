FROM maven:eclipse-temurin AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
COPY pom.xml .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /src/target/assessment-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080 SPRING_DATASOURCE_URL=
ENV SPRING_DATA_MONGODB_URI= SPRING_DATA_MONGODB_DATABASE=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar