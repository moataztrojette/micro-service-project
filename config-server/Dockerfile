FROM eclipse-temurin:18-jdk-alpine
EXPOSE 8888
ADD target/config-server*.jar config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]