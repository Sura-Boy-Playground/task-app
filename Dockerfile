FROM maven

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src
RUN mvn package -DskipTests

ENTRYPOINT ["java", "-jar", "target/task-api.jar"]
EXPOSE 8080