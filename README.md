# Lash Manager — Backend

Sistema de gestão para salões de lash design (extensão de cílios). API REST em Java/Spring Boot, arquitetura hexagonal (Ports & Adapters) multi-módulo, com suporte a multi-tenancy (schema-per-tenant).

## Stack

- **Java 21** + **Spring Boot 3.3.5**
- **Maven** (multi-módulo: `lash-core`, `lash-clients`, `lash-services`, `lash-appointments`, `lash-finance`, `lash-stock`, `lash-fichas`, `lash-dashboard`, `lash-app`)
- **PostgreSQL** — migrations via **Flyway** (schema `public`) e **Liquibase** (schemas de tenant, provisionados sob demanda)
- **Spring Security** + **JWT**

## Rodando localmente (via Docker — recomendado)

Requer [Docker](https://www.docker.com/products/docker-desktop) instalado e o repositório [`lash-frontend`](https://github.com/Guilherme-ACouto/lash-pro-frontend) clonado como pasta irmã deste (`lash-backend`).

```bash
cp .env.example .env
# edite o .env e preencha JWT_SECRET (ex: openssl rand -hex 32)

docker compose up --build
```

Isso sobe Postgres + backend + frontend juntos, já conectados. Backend em `http://localhost:8080`, frontend em `http://localhost:4200`.

## Rodando localmente (sem Docker)

Pré-requisitos: Java 21, Maven, PostgreSQL rodando na porta `5433` com banco `lashmanager` criado.

```bash
sdk use java 21.0.5-zulu
```

### Instalação de dependências

```bash
mvn install -DskipTests
```

### Verificação de código (linter)

Não há linter/formatter configurado no projeto no momento (ex.: Checkstyle, Spotless, PMD).

### Execução dos testes

```bash
mvn test                      # roda todos os testes do reactor (todos os módulos)
mvn test -Dtest=NomeDaClasse  # roda uma única classe de teste
```

### Build (produção)

```bash
mvn package -DskipTests -pl lash-app -am
# gera o jar executável em lash-app/target/lash-app-1.0.0.jar
```

### Execução (modo dev, sem gerar jar)

```bash
mvn spring-boot:run
# sobe a API em localhost:8080
```

## Variáveis de ambiente

Todas têm defaults para desenvolvimento local — ver `.env.example`.

| Variável | Default local |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5433/lashmanager` |
| `DB_USER` / `DB_PASS` | `postgres` |
| `JWT_SECRET` | obrigatório — sem default no Docker (gerar via `openssl rand -hex 32`) |
| `CORS_ORIGINS` | `http://localhost:4200` |

## Credenciais de dev (seed)

`admin@lashmanager.com` / `admin123`

## Repositórios relacionados

- Frontend: https://github.com/Guilherme-ACouto/lash-pro-frontend
- Documentação/specs: https://github.com/Guilherme-ACouto/lash-pro-docs
