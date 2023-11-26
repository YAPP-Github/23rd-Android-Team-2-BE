FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/moneymong-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
