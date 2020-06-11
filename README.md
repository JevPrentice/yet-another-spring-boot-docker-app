# Yet another Spring Boot Docker Application

This project is yet another simple service written with Java and Spring boot.

Add the following to /etc/hosts to run application without docker.
127.0.0.1 localhost host.docker.internal

mvn spring-boot:build-image -Dspring-boot.build-image.imageName=jevprentice/yet-another-spring-boot-docker-app
docker run -p 8088:8088 -t jevprentice/yet-another-spring-boot-docker-app

### Register a new user
    curl -H "Content-Type: application/json" -X POST http://localhost:8088/api/users/register -d '{"username": "admin", "password": "password"}' | jq
### Login as an existing user
    curl -H "Content-Type: application/json" -X POST http://localhost:8088/api/users/login -d '{"username": "admin", "password": "password"}' | jq '.authHeader'
### Get a human being
    curl -H "Content-Type: application/json" -X GET http://localhost:8088/api/human/2 -H  | jq
