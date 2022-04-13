FROM openjdk:11.0.14
ADD target/tenpo-challenge-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]