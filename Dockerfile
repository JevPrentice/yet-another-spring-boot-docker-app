FROM openjdk:12-alpine
MAINTAINER Jev Prentice

# Copy the application jar
COPY target/dependency /usr/share/yet-another-spring-boot-docker-app/lib
COPY target/yet-another-spring-boot-docker-app.jar /usr/share/yet-another-spring-boot-docker-app/yet-another-spring-boot-docker-app.jar

# Create volumes
VOLUME ["/data", "/tmp"]

# Expose the service on a port
EXPOSE 8080

# Create entry point for service
ENTRYPOINT ["java", "-jar", "/usr/share/yet-another-spring-boot-docker-app/yet-another-spring-boot-docker-app.jar"]
