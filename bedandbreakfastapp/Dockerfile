FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /app


COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app 

COPY --from=builder /app/target/assessment-0.0.1-SNAPSHOT.jar bnbapp.jar


ENV PORT=8080 
ENV SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/bedandbreakfast

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar bnbapp.jar