FROM azul/zulu-openjdk-alpine:11.0.23-11.72-jre
ARG JAR_FILE=build/libs//*.jar
COPY ${JAR_FILE} auth-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/auth-1.0-SNAPSHOT.jar"]