FROM amazoncorretto:21-alpine-jdk AS builder
ARG JAR_FILE=web/target/web.jar
COPY ${JAR_FILE} web.jar
RUN java -Djarmode=layertools -jar web.jar extract

FROM amazoncorretto:21-alpine-jdk
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher"]