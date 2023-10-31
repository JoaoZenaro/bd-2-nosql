# Build stage: cache maven dependencies
FROM maven:3.8.4-openjdk-17-slim AS mavendeps

WORKDIR /home/app

COPY pom.xml .

RUN mvn dependency:go-offline


# Build stage: app (using cached dependencies)
FROM mavendeps AS build

COPY src ./src

RUN mvn clean package


# Package stage
FROM openjdk:17-jdk-slim

WORKDIR /usr/local/lib/

COPY --from=build /home/app/target/maven-redis-0.0.1-SNAPSHOT.jar maven-redis.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "maven-redis.jar"]
