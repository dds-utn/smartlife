FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

ARG SERVICE_NAME=trends-service

COPY . .

RUN mvn clean package -pl ${SERVICE_NAME} -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

ARG SERVICE_NAME=trends-service

COPY --from=builder /app/${SERVICE_NAME}/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
