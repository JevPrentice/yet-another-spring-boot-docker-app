# Yet another Spring Boot Docker Application

This project is yet another simple service written with Java and Spring boot, the project is built into a openjdk:12 docker image using Maven.

## Build project and local image with maven, then push to dockerhub ( Mush be signed in )

mvn clean install && docker push jevprentice/yet-another-spring-boot-docker-app:latest

## Docker swarm (An image mush be available on dockerhub - https://cloud.docker.com/repository/registry-1.docker.io/jevprentice/yet-another-spring-boot-docker-app/tags)

docker swarm init

mkdir -p /tmp/yet-another-spring-boot-docker-app/data/pgdata

docker stack deploy -c docker-compose.yml yet-another-spring-boot-docker-app --with-registry-auth

## Docker and docker stack helpful commands

docker stack services yet-another-spring-boot-docker-app

docker stack ps yet-another-spring-boot-docker-app

docker service logs yet-another-spring-boot-docker-app_web

docker service logs yet-another-spring-boot-docker-app_db

docker stack rm yet-another-spring-boot-docker-app

## Build and push docker image (Without Maven)

### Build image

docker build -t jevprentice/yet-another-spring-boot-docker-app:latest .

### Push image ( Must be signed in )

docker push jevprentice/yet-another-spring-boot-docker-app:latest

## Access Postgres

export PGPASSWORD="postgres_password" && "/Applications/Postgres.app/Contents/Versions/11/bin/psql" -p5432 -d "yet-another-spring-boot-docker-app" -U "postgres_username" -h localhost
