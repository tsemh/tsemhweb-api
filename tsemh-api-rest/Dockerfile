# Estágio 1: Construir a aplicação
FROM maven:3.8.5-openjdk-17 as build
WORKDIR /src/app
COPY . /src/app
RUN mvn clean package

# Estágio 2: Criar a imagem final
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /src/app/target/tsemh-api-rest-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
