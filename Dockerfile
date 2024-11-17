# Build stage
FROM gradle:7.6.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

# Run stage
FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/src/build/libs/*-boot.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]