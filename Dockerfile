FROM openjdk:11 as builder
EXPOSE 8080
WORKDIR /application
ARG JAR_FILE=target/toDoAppWithLogin.jar
COPY $JAR_FILE application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]