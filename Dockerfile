FROM acntechie/jre

ARG APPLICATION_NAME

COPY ./web/target/${APPLICATION_NAME}.war /application.war
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]