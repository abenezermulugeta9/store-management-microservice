# Define a base image from docker hub
FROM openjdk:11
# Variable to store the path for the build artifact from the project
ARG JAR_FILE=target/*.jar
# Create a new jar file in the image and copy the content of the artifiact from the project
COPY ${JAR_FILE} configserver.jar
# Run the artifcat whenever the container starts i.e.("java -jar serviceregistry.jar")
ENTRYPOINT ["java", "-jar", "/configserver.jar"]
# Specify a port number
EXPOSE 9296