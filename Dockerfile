FROM eclipse-temurin:17 AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

RUN ls -l /app/target

FROM eclipse-temurin:23-jre-alpine AS runner

WORKDIR /app

COPY --from=build /app/target/*.jar /app/service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "service.jar"]