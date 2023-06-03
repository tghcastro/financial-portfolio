FROM eclipse-temurin:17.0.7_7-jdk-jammy

USER root

LABEL maintainer="Thiago de Castro - tghcastro"

ENV DOCKERIZE_VERSION v0.7.0

RUN apt-get update && apt-get install -y curl \
    && curl -L https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz -o dockerize.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize.tar.gz \
    && rm dockerize.tar.gz

ENV APP_HOME /apps

RUN mkdir $APP_HOME

WORKDIR $APP_HOME

# COPY GRADLE BASE STRUCTURE
COPY gradlew .
COPY gradle ./gradle

# DOWNLOADING DEPENDENCIES TO REUSE DOCKER CACHE
RUN ./gradlew --refresh-dependencies

# COPY PROJECT
COPY settings.gradle .
COPY build.gradle .
COPY stocks-service ./stocks-service
COPY portfolio-service ./portfolio-service

RUN ./gradlew build --exclude-task test


