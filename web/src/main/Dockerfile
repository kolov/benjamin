FROM java:8

WORKDIR /app
COPY target/benjamin-*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar" ]