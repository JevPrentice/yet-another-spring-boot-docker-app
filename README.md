# DockerJava12

Simple PingPong service written in Java built using Maven and Docker (openjdk:12)

## Build project and local docker image with maven
mvn clean install

## Build and push docker images
docker build -t jevprentice/docker-java12-pingpongservice:1.0 .
docker push jevprentice/docker-java12-pingpongservice:1.0

## Run docker container
docker run -d --name=docker-java12-pingpongservice --restart always -p 8082:8082 -v /tmp/docker-java12-pingpongservice/data:/data jevprentice/docker-java12-pingpongservice:1.0

## Run Stack (An image mush be available on dockerhub)
docker swarm init
docker stack deploy -c docker-compose.yml docker-java12-pingpongservice --with-registry-auth
docker stack services docker-java12-pingpongservice
