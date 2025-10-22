# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/authentication-0.0.1-SNAPSHOT.jar split-money-snapshot.jar

# Create log directory
RUN mkdir -p /app/logs

# Expose the port your application will run on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "split-money-snapshot.jar"]

