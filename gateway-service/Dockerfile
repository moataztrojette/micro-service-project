FROM eclipse-temurin:18-jdk-alpine
EXPOSE 9090
ADD target/gateway-service*.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]