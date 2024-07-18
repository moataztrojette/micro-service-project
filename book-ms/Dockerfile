FROM eclipse-temurin:18-jdk-alpine
EXPOSE 8081
ADD target/book-ms*.jar book-ms.jar
ENTRYPOINT ["java", "-jar", "book-ms.jar"]