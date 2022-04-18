FROM openjdk:11.0-jre-slim

COPY target/kasir-api-0.0.1-SNAPSHOT.jar /app/kasir-api-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar" , "/app/kasir-api-0.0.1-SNAPSHOT.jar"]