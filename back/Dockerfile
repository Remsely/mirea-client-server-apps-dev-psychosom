FROM gradle:8.10-jdk21-corretto as builder
WORKDIR /app
COPY . .

# RUN chmod +x gradlew

RUN gradle clean build -x test

FROM amazoncorretto:21
WORKDIR /app
COPY --from=builder /app/app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
