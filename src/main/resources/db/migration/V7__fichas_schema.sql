CREATE TABLE anamneses (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id               UUID NOT NULL UNIQUE REFERENCES clients(id),
    guardian_name           VARCHAR(255),
    address                 VARCHAR(255),
    neighborhood            VARCHAR(100),
    city                    VARCHAR(100),
    state                   VARCHAR(2),
    birth_date              DATE,
    phone                   VARCHAR(20),
    cpf                     VARCHAR(14),
    rg                      VARCHAR(20),
    had_lash_extensions     BOOLEAN NOT NULL DEFAULT false,
    wears_mascara           BOOLEAN NOT NULL DEFAULT false,
    has_allergies           BOOLEAN NOT NULL DEFAULT false,
    has_thyroid_issues      BOOLEAN NOT NULL DEFAULT false,
    sleep_side              VARCHAR(20)  NOT NULL DEFAULT 'AMBOS',
    had_eye_procedure       BOOLEAN NOT NULL DEFAULT false,
    is_pregnant_or_nursing  BOOLEAN NOT NULL DEFAULT false,
    had_oncological_treatment BOOLEAN NOT NULL DEFAULT false,
    has_skin_disease        BOOLEAN NOT NULL DEFAULT false,
    has_health_treatment    BOOLEAN NOT NULL DEFAULT false,
    uses_medication         BOOLEAN NOT NULL DEFAULT false,
    term_accepted           BOOLEAN NOT NULL DEFAULT false,
    term_accepted_at        TIMESTAMP,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE anamnese_tokens (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id  UUID NOT NULL REFERENCES clients(id),
    token      VARCHAR(64) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    used       BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE lash_mappings (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id      UUID NOT NULL REFERENCES clients(id),
    mapping_date   DATE NOT NULL,
    mapping_type   VARCHAR(100),
    curvature      VARCHAR(10),
    humidity       VARCHAR(50),
    temperature    VARCHAR(50),
    thickness      VARCHAR(10),
    thread_brand   VARCHAR(100),
    thread_format  VARCHAR(50),
    adhesive       VARCHAR(100),
    lengths_used   VARCHAR(255),
    observations   TEXT,
    canvas_data    TEXT,
    photo_before   TEXT,
    photo_after    TEXT,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_anamnese_tokens_token     ON anamnese_tokens(token);
CREATE INDEX idx_lash_mappings_client_id   ON lash_mappings(client_id);
CREATE INDEX idx_lash_mappings_date        ON lash_mappings(mapping_date DESC);
