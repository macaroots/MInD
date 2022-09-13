FROM openjdk:8-jdk

COPY target/mind-0.0.6.jar /app/mind.jar

CMD ["java", "-jar", "/app/mind.jar"]
