
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/hello-world-0.0.1-SNAPSHOT.jar.original app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
