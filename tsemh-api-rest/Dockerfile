FROM maven:3.8.5-openjdk-17 as build
RUN mkdir -p /src/app
WORKDIR  /src/app
ADD . /src/app

RUN mvn clean package

FROM eclipse-temurin:17-jdk
RUN mkdir -p /src/app
WORKDIR  /src/app
COPY --from=build /src/app/target/tsemh-api-rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
 