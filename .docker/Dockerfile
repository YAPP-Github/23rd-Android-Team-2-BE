FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/MoneyMong-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
