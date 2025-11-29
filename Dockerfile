FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/expense-tracker-0.0.1-SNAPSHOT.jar expense-tracker-v1.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "expense-tracker-v1.0.jar"]