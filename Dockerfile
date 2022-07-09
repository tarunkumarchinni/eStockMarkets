FROM openjdk:11-jdk-slim 
ADD target/*.jar estockmarketapp.jar 
EXPOSE 8084
ENTRYPOINT ["java","-jar","estockmarketapp.jar"]