FROM eclipse-temurin:18-jdk-alpine
EXPOSE 8761
ADD target/server-discovery*.jar server-discovery.jar
ENTRYPOINT ["java", "-jar", "server-discovery.jar"]