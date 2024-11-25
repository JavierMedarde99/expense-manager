FROM maven:3-amazoncorretto-21-alpine AS build
COPY /web .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk 
COPY --from=build /target/web.jar web.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/web.jar", "org.springframework.boot.loader.launch.JarLauncher"]