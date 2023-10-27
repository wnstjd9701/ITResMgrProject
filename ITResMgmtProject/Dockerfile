FROM openjdk:17
CMD ["./mvnw", "package", "-DuserTests=true"]
ARG JAR_FILE_PATH=./target/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "app.jar"]
