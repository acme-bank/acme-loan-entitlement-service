#!/bin/sh

set -e

SPRING_PROFILE=${SPRING_PROFILE:-dev}
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${SPRING_PROFILE}"

java ${JAVA_OPTS} -jar /application.war
