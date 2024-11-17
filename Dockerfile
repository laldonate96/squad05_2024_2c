FROM openjdk:11-jdk-slim
WORKDIR /app
COPY build/libs/*-boot.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]