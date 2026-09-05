# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copia todos os pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .
COPY lash-core/pom.xml lash-core/
COPY lash-clients/pom.xml lash-clients/
COPY lash-services/pom.xml lash-services/
COPY lash-appointments/pom.xml lash-appointments/
COPY lash-finance/pom.xml lash-finance/
COPY lash-stock/pom.xml lash-stock/
COPY lash-fichas/pom.xml lash-fichas/
COPY lash-dashboard/pom.xml lash-dashboard/
COPY lash-app/pom.xml lash-app/
RUN --mount=type=cache,target=/root/.m2 mvn -B -pl lash-app -am dependency:go-offline

# Agora copia o código-fonte e builda o jar executável (lash-app)
COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn -B -pl lash-app -am clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S lash && adduser -S lash -G lash
COPY --from=build /workspace/lash-app/target/lash-app-*.jar app.jar
USER lash

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
