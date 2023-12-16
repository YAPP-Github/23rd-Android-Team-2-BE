FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/moneymong-0.0.1.jar
ENV ACTIVE_PROFILE=${ACTIVE_PROFILE} \
DB_URL=${DB_URL}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar","/app.jar"]
