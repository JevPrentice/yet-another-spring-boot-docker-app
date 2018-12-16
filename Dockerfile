FROM openjdk:12
MAINTAINER Jev Prentice

ENV java="/usr/lib/jvm/java-12-openjdk-amd64/bin/java"

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib /usr/share/docker-java12-pingpongservice/lib
# Add the service itself
ARG JAR_FILE
#ADD target/${JAR_FILE} /usr/share/docker-java12-pingpongservice/pingpongservice.jar
ADD target/docker-java12-pingpongservice.jar /usr/share/docker-java12-pingpongservice/docker-java12-pingpongservice.jar

VOLUME ["/data"]

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/usr/share/docker-java12-pingpongservice/docker-java12-pingpongservice.jar"]
