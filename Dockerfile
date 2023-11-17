FROM eclipse-temurin:17.0.8_7-jre-alpine
WORKDIR /app
COPY applications/app-service/build/libs/JavaEnvoyExtAuthz.jar app-service.jar

RUN apk update && apk upgrade && \
    apk add bash && \
    apk add curl && \
    apk add aws-cli && \
    apk add git

ENV JAVA_OPTS="-XX:+UseContainerSupport"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app-service.jar"]
