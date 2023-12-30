# Use the official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the Maven project
RUN mvn clean install  -DskipTests

# Use a smaller base image for the runtime
FROM openjdk:17-alpine3.14

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime stage
COPY --from=builder /app/target/catchy-0.0.1-*.jar ./catchy-0.0.1-SNAPSHOT.jar

# Specify the command to run your application
CMD ["java", "-jar", "catchy-0.0.1-SNAPSHOT.jar"]

