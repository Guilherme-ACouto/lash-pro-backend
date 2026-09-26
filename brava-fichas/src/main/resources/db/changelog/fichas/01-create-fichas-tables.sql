CREATE TABLE fichas (
    id                          UUID PRIMARY KEY,
    client_id                   UUID           NOT NULL UNIQUE REFERENCES clients(id),
    client_name                 VARCHAR(200)   NOT NULL,
    date                        DATE           NOT NULL,
    skin_type                   VARCHAR(50),
    eye_shape                   VARCHAR(100),
    has_allergies               BOOLEAN        NOT NULL DEFAULT FALSE,
    allergies_description       TEXT,
    has_medications             BOOLEAN        NOT NULL DEFAULT FALSE,
    medications_description     TEXT,
    has_sensitivities           BOOLEAN        NOT NULL DEFAULT FALSE,
    sensitivities_description   TEXT,
    observations                TEXT,
    active                      BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at                  TIMESTAMP      NOT NULL,
    updated_at                  TIMESTAMP      NOT NULL
);

CREATE TABLE lash_mappings (
    id               UUID PRIMARY KEY,
    ficha_id         UUID           NOT NULL REFERENCES fichas(id),
    appointment_id   UUID,
    date             DATE           NOT NULL,
    technique        VARCHAR(100),
    curvature        VARCHAR(20),
    thickness        VARCHAR(20),
    length           VARCHAR(50),
    right_eye_notes  TEXT,
    left_eye_notes   TEXT,
    notes            TEXT,
    created_at       TIMESTAMP      NOT NULL,
    updated_at       TIMESTAMP      NOT NULL
);

CREATE INDEX idx_fichas_client     ON fichas(client_id);
CREATE INDEX idx_fichas_active     ON fichas(active);
CREATE INDEX idx_mappings_ficha    ON lash_mappings(ficha_id);
CREATE INDEX idx_mappings_appt     ON lash_mappings(appointment_id);
CREATE INDEX idx_mappings_date     ON lash_mappings(date);
