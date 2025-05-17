FROM maven:3.9.9-eclipse-temurin-21-jammy as builder
COPY . .
RUN mvn --no-transfer-progress clean package -Dmaven.test.skip
FROM eclipse-temurin:21.0.7_6-jdk
COPY --from=builder /target/ScraperApi-0.0.1-SNAPSHOT.jar /backend.jar
COPY /src/main/resources/postgresql/tls/RootCA.pem /root/.postgresql/root.crt
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/backend.jar"]