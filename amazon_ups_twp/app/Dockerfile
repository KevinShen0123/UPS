# Use an official Gradle image as a build image
FROM gradle:latest as builder

# Set the working directory to /app
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle .

# Copy the application source code
COPY src src

# Build the application with Gradle
RUN gradle build

# Use an official OpenJDK runtime as the final image
FROM openjdk:17-alpine
# Set the working directory to /app
WORKDIR /app
COPY build/libs/app.jar app.jar
# Copy the JAR file built by Gradle from the builder image

# Expose the port that the application will run on
EXPOSE 8080
# Set an environment variable with the JAR file path

# Specify the command to run on container startup
CMD ["java", "-jar", "app.jar"]
