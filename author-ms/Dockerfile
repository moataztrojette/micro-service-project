FROM eclipse-temurin:18-jdk-alpine
EXPOSE 8083
ADD target/author-ms*.jar author-ms.jar
ENTRYPOINT ["java", "-jar", "author-ms.jar"]