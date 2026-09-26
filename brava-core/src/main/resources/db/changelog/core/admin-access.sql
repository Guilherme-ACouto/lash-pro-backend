-- Administração da assinatura (módulo Configurações).
-- Camada "grossa" de acesso no public, igual à Pontta: o usuário é ou não administrador da assinatura.
-- A camada fina (permissão por módulo/ação) mora no schema do tenant (core-tenant-changelog).

ALTER TABLE users ADD COLUMN admin BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE users ADD COLUMN last_login_at TIMESTAMP;
ALTER TABLE users ADD COLUMN token_version INTEGER NOT NULL DEFAULT 0;

UPDATE users SET admin = TRUE WHERE role = 'OWNER';

-- role (OWNER/ASSISTANT) deixa de ser usado pelo código: substituído por admin + permissões.
-- Mantido só por histórico (changesets antigos já aplicados ainda o preenchem).
ALTER TABLE users ALTER COLUMN role DROP NOT NULL;
ALTER TABLE users ALTER COLUMN role DROP DEFAULT;

-- Titular da assinatura (equivalente ao contractor da Signature da Pontta). Sem FK de propósito:
-- tenants e users se referenciam mutuamente (users.tenant_id já tem FK pra cá).
ALTER TABLE tenants ADD COLUMN owner_user_id UUID;

UPDATE tenants t
SET owner_user_id = (
    SELECT u.id FROM users u
    WHERE u.tenant_id = t.id AND u.role = 'OWNER'
    ORDER BY u.created_at
    LIMIT 1
);

-- Convite pra entrar numa assinatura (equivalente ao signature_invite da Pontta). O usuário só é
-- criado na confirmação — o convite guarda o que foi definido pela administradora até lá.
CREATE TABLE tenant_invite (
    id           UUID PRIMARY KEY,
    tenant_id    UUID         NOT NULL REFERENCES tenants(id),
    email        VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    admin        BOOLEAN      NOT NULL DEFAULT FALSE,
    professional BOOLEAN      NOT NULL DEFAULT FALSE,
    permissions  TEXT,
    token        VARCHAR(255) NOT NULL UNIQUE,
    status       VARCHAR(20)  NOT NULL,
    expires_at   TIMESTAMP    NOT NULL,
    confirmed_at TIMESTAMP,
    invited_by   UUID,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_tenant_invite_status CHECK (status IN ('PENDING', 'CONFIRMED'))
);

CREATE INDEX idx_tenant_invite_tenant ON tenant_invite(tenant_id, status);
CREATE INDEX idx_tenant_invite_token ON tenant_invite(token);

-- Auditoria passa a saber de qual assinatura é cada comando (antes misturava todas).
ALTER TABLE command_audit_log ADD COLUMN tenant_id UUID;
ALTER TABLE command_audit_log ADD COLUMN user_name VARCHAR(255);

CREATE INDEX idx_command_audit_log_tenant ON command_audit_log(tenant_id, executed_at);
CREATE INDEX idx_command_audit_log_user ON command_audit_log(user_id);
