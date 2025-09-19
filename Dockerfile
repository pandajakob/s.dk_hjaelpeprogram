FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080