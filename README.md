# Lash Manager — Backend

Sistema de gestão para salões de lash design (extensão de cílios). API REST em Java/Spring Boot, arquitetura hexagonal (Ports & Adapters) multi-módulo, com suporte a multi-tenancy (schema-per-tenant) e padrão CQRS (Command/ApplicationService na escrita, QueryService na leitura).

## Stack

- **Java 21** + **Spring Boot 3.3.5**
- **Maven** (multi-módulo: `lash-core`, `lash-clients`, `lash-services`, `lash-appointments`, `lash-finance`, `lash-stock`, `lash-fichas`, `lash-dashboard`, `lash-app`)
- **PostgreSQL** — migrations via **Flyway** (schema `public`) e **Liquibase** (schemas de tenant, provisionados sob demanda)
- **Spring Security** + **JWT**

## Arquitetura

Cada módulo de negócio (`clients`, `services`, `appointments`, `finance`, `stock`, `fichas`) segue o mesmo padrão de escrita/leitura:

```
Escrita:  HTTP (@RequestBody = Command) → {Agregado}Resource → ApplicationService.when(Command)
              → {Agregado}UseCase (regra de negócio) → entidade de domínio → Repository → DB
              → resposta: corpo = a própria entidade (sem DTO), headers X-lash-alert/X-lash-params

Leitura:  HTTP → {Agregado}QueryResource → {Agregado}QueryService → QueryRepository → DB
```

Sem `*Request`/`*Response` DTO — o `Command` da aplicação é o próprio corpo da requisição, a entidade de domínio é o próprio corpo da resposta. Erros seguem o formato `{code, message, customCode, infoUrl, details}`. Detalhes completos em `lash-docs/.specs/codebase/ARCHITECTURE.md` e `CLAUDE.md` (raiz do monorepo).

### Endpoints principais

| Módulo | Base | Recurso |
|---|---|---|
| Auth/Tenant | `/api/auth`, `/api/register`, `/api/activation`, `/api/admin/tenants` | Login, registro, ativação, admin de tenants |
| Clientes | `/api/clients` | CRUD + desativar/reativar |
| Serviços | `/api/services` | CRUD + desativar/reativar |
| Agendamentos | `/api/appointments` | CRUD + confirmar/completar/cancelar/não-compareceu |
| Financeiro | `/api/financial/entries`, `/summary`, `/categories` | Lançamentos, resumo, categorias |
| Estoque | `/api/inventory/items`, `/api/inventory/movements` | Itens, compra, saída manual |
| Anamnese + Mapeamento | `/api/anamnese`, `/api/public/anamnese/{token}`, `/api/mappings` | Ficha de anamnese (upsert por cliente), link público sem login, mapeamentos de cílios |
| Dashboard | `/api/dashboard?period=TODAY\|WEEK\|MONTH` | Indicadores agregados (só leitura) |

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
| `ANAMNESE_LINK_HOURS` | `72` — validade do link público de anamnese sem login |

## Credenciais de dev (seed)

`admin@lashmanager.com` / `admin123`

## Repositórios relacionados

- Frontend: https://github.com/Guilherme-ACouto/lash-pro-frontend
- Documentação/specs: https://github.com/Guilherme-ACouto/lash-pro-docs
