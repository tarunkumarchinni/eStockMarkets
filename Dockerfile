FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD target/estockmarketapp.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]