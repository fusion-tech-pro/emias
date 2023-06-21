FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:11-jdk-slim

COPY --from=build /app/target/EMIAS-0.0.1-SNAPSHOT.jar /app.jar
COPY .env /app/env_file.env

ENTRYPOINT ["java","-jar","/app.jar"]
