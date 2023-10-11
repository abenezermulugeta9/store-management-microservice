# Sets the base image for the container
FROM openjdk:11

# Declares an argument JAR_FILE that can be passed to the Docker build command
ARG JAR_FILE=target/*.jar

# Copies the JAR file from the host machine to the container image
COPY ${JAR_FILE} cloudgateway.jar

# Sets the default command that will be executed when a container is started from this image
ENTRYPOINT ["java", "-jar", "/cloudgateway.jar"]

# Indicates the container will listen on port 9090
EXPOSE 9090