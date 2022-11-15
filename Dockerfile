FROM adoptopenjdk/openjdk8
# in case alpine kernel for linux os (small distro):
# FROM adoptopenjdk/openjdk8:alpine-jre

# pre-requisite: ./target jar present in repo:
ARG JAR_FILE_HOST_LOCATION=target/pricing*.jar

WORKDIR /usr/app/

COPY ${JAR_FILE_HOST_LOCATION} app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]