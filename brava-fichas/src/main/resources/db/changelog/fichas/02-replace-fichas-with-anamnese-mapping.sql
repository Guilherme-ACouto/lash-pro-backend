-- Substitui o domínio antigo de fichas (skinType/eyeShape/technique, sem consumidor real no
-- frontend) pelo domínio real usado pela feature de Anamnese + Mapeamento já existente no
-- frontend (features/fichas/). Ver brava-docs/.specs/features/anamnese/spec.md.
DROP TABLE IF EXISTS lash_mappings;
DROP TABLE IF EXISTS fichas;

CREATE TABLE anamneses (
    id                          UUID PRIMARY KEY,
    client_id                   UUID           NOT NULL UNIQUE REFERENCES clients(id),
    client_name                 VARCHAR(200)   NOT NULL,
    guardian_name               VARCHAR(200),
    address                     VARCHAR(300),
    neighborhood                VARCHAR(150),
    city                        VARCHAR(150),
    state                       VARCHAR(2),
    birth_date                  DATE,
    phone                       VARCHAR(20),
    cpf                         VARCHAR(14),
    rg                          VARCHAR(20),
    had_lash_extensions         BOOLEAN        NOT NULL DEFAULT FALSE,
    wears_mascara               BOOLEAN        NOT NULL DEFAULT FALSE,
    has_allergies               BOOLEAN        NOT NULL DEFAULT FALSE,
    has_thyroid_issues          BOOLEAN        NOT NULL DEFAULT FALSE,
    sleep_side                  VARCHAR(20),
    had_eye_procedure           BOOLEAN        NOT NULL DEFAULT FALSE,
    is_pregnant_or_nursing      BOOLEAN        NOT NULL DEFAULT FALSE,
    had_oncological_treatment   BOOLEAN        NOT NULL DEFAULT FALSE,
    has_skin_disease            BOOLEAN        NOT NULL DEFAULT FALSE,
    has_health_treatment        BOOLEAN        NOT NULL DEFAULT FALSE,
    uses_medication             BOOLEAN        NOT NULL DEFAULT FALSE,
    term_accepted                BOOLEAN       NOT NULL DEFAULT FALSE,
    term_accepted_at              TIMESTAMP,
    link_token                    VARCHAR(64),
    link_token_expiry              TIMESTAMP,
    created_at                    TIMESTAMP     NOT NULL,
    updated_at                    TIMESTAMP     NOT NULL
);

CREATE TABLE mappings (
    id              UUID PRIMARY KEY,
    client_id       UUID           NOT NULL REFERENCES clients(id),
    client_name     VARCHAR(200)   NOT NULL,
    mapping_date    DATE           NOT NULL,
    mapping_type    VARCHAR(100),
    curvature       VARCHAR(20),
    humidity        VARCHAR(20),
    temperature     VARCHAR(20),
    thickness       VARCHAR(20),
    thread_brand    VARCHAR(100),
    thread_format   VARCHAR(50),
    adhesive        VARCHAR(100),
    lengths_used    VARCHAR(200),
    observations    TEXT,
    canvas_data     TEXT,
    photo_before    TEXT,
    photo_after     TEXT,
    created_at      TIMESTAMP      NOT NULL,
    updated_at      TIMESTAMP      NOT NULL
);

CREATE INDEX idx_anamneses_client      ON anamneses(client_id);
CREATE INDEX idx_anamneses_link_token  ON anamneses(link_token);
CREATE INDEX idx_mappings_client       ON mappings(client_id);
CREATE INDEX idx_mappings_date         ON mappings(mapping_date);
