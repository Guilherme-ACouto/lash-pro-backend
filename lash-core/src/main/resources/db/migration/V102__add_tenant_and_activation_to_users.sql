ALTER TABLE users
    ADD COLUMN tenant_id             UUID REFERENCES tenants(id),
    ADD COLUMN activation_key        VARCHAR(255),
    ADD COLUMN activation_key_expiry TIMESTAMP;

CREATE INDEX idx_users_tenant_id ON users(tenant_id);
CREATE INDEX idx_users_activation_key ON users(activation_key);
