# DockerJava10

Simple PingPong service written in Java built using Maven and Docker (openjdk:10)

# Build docker image
docker build -t dockerjava10/pingpong .

# Run docker image in foreground
docker run -p 8082:8082 dockerjava10/pingpong

# Run docker image detached
docker run -d -p 8082:8082 dockerjava10/pingpong

# List docker containers
docker ps

# Stop docker container
docker stop ${CONTAINER ID}
