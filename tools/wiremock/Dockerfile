FROM openjdk:11-jre

ENV WIREMOCK_VERSION 2.35.0

RUN apt-get update && apt-get install -y wget

ENV WIREMOCK_HOME "/home/wiremock"

RUN mkdir -p $WIREMOCK_HOME/lib/ \
    && wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-jre8-standalone/$WIREMOCK_VERSION/wiremock-jre8-standalone-$WIREMOCK_VERSION.jar \
    -O $WIREMOCK_HOME/lib/wiremock-jre8-standalone.jar

WORKDIR $WIREMOCK_HOME

EXPOSE 8080 8443

COPY tools/wiremock/mappings/*.json ./mappings/