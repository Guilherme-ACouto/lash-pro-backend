# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copia todos os pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .
COPY brava-core/pom.xml brava-core/
COPY brava-clients/pom.xml brava-clients/
COPY brava-services/pom.xml brava-services/
COPY brava-appointments/pom.xml brava-appointments/
COPY brava-finance/pom.xml brava-finance/
COPY brava-stock/pom.xml brava-stock/
COPY brava-fichas/pom.xml brava-fichas/
COPY brava-dashboard/pom.xml brava-dashboard/
COPY brava-app/pom.xml brava-app/
RUN --mount=type=cache,target=/root/.m2 mvn -B -pl brava-app -am dependency:go-offline

# Agora copia o código-fonte e builda o jar executável (brava-app)
COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn -B -pl brava-app -am clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S brava && adduser -S brava -G brava
COPY --from=build /workspace/brava-app/target/brava-app-*.jar app.jar
USER brava

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
