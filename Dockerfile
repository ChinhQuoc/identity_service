# Stage 1: build
# Start with a Maven image that includes JDK 26
FROM maven:4.0.0-rc-5-eclipse-temurin-26 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn package

#Stage 2: create image
# Start with Amazon Correto JDK 26
FROM eclipse-temurin:26

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]