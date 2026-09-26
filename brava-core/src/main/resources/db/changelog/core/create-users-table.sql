CREATE TABLE users (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                        VARCHAR(255)   NOT NULL,
    email                       VARCHAR(255)   NOT NULL UNIQUE,
    password                    VARCHAR(255)   NOT NULL,
    role                        VARCHAR(50)    NOT NULL DEFAULT 'OWNER',
    active                      BOOLEAN        NOT NULL DEFAULT TRUE,
    tenant_id                   UUID           NOT NULL REFERENCES tenants(id),
    activation_key              VARCHAR(255),
    activation_key_expiry       TIMESTAMP,
    password_reset_token        VARCHAR(255),
    password_reset_token_expiry TIMESTAMP,
    created_at                  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_tenant_id ON users(tenant_id);
CREATE INDEX idx_users_activation_key ON users(activation_key);
