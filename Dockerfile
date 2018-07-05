FROM openjdk:10
RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y maven

ENV java="/usr/lib/jvm/java-10-openjdk-amd64/bin/java"

WORKDIR /workdir

# Prepare by downloading dependencies
ADD pom.xml /workdir/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Add source
ADD src /workdir/src

# Compile and package into a fat jar
RUN ["mvn", "package"]

EXPOSE 8082
ENTRYPOINT ["java"]
CMD ["-jar", "/workdir/target/DockerJava10-1.0-SNAPSHOT-jar-with-dependencies.jar"]
