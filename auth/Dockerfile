FROM azul/zulu-openjdk-alpine:11
ARG JAR_FILE=build/libs//*.jar
COPY ${JAR_FILE} ProjectAuth-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ProjectAuth-1.0-SNAPSHOT.jar"]