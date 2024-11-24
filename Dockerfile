FROM maven:3-amazoncorretto-21-alpine AS builder
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:21-alpine-jdk 
WORKDIR /app
COPY --from=build /usr/src/app/target/web.jar /usr/app/web.jar  
RUN java -Djarmode=layertools -jar web.jar extract

EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher"]