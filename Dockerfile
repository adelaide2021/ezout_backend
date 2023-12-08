FROM openjdk:17-oracle

WORKDIR /app

COPY target/ezout_backend-0.0.1-SNAPSHOT.jar /app/app.jar

COPY src/main/resources/application.yml /app/application.yml

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
