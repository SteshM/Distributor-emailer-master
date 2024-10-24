FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ./target/EmailSender-0.0.1-SNAPSHOT.jar email.jar
ENTRYPOINT ["java", "-jar", "/email.jar"]