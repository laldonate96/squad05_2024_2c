# Build stage
FROM gradle:7.6.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

# Run stage
FROM openjdk:11-jre-slim

# Copy the jar
COPY --from=build /home/gradle/src/build/libs/*-boot.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]