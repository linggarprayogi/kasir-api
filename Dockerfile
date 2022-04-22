# syntax=docker/dockerfile:1

#FROM openjdk:11.0.8

#WORKDIR /app

#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline

#COPY src ./src

#CMD ["./mvnw", "spring-boot:run"]

FROM openjdk:11.0-jre-slim

COPY target/kasir-api-*.jar /app/kasir-api-*.jar

EXPOSE 9191

CMD ["java", "-jar" , "/app/kasir-api-*.jar"]

