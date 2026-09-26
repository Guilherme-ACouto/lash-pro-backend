CREATE TABLE tenants (
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    schema_name VARCHAR(63)  NOT NULL UNIQUE,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_tenants_schema_name ON tenants(schema_name);
