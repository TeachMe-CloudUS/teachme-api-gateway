
FROM eclipse-temurin:17 as build

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine as runner
WORKDIR /app
COPY --from=build /app/target/*.jar /app/service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "service.jar"]