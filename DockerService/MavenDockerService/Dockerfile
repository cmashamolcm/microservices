FROM openjdk:16
ADD target/*app.jar maven-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "maven-docker.jar"]
