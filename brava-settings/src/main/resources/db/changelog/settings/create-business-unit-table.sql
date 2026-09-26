-- Unidade de negócio (empresa/filial), no schema do tenant — como a businessunit da Pontta.
-- Uma assinatura pode ter várias no futuro (filiais); hoje a tela edita só a principal (main).
-- Documento e endereço ficam direto aqui (na Pontta vão numa tabela contact genérica, que o
-- Brava Pro ainda não tem). O logo vai pra storage — aqui só a chave e a URL (logo_key/logo_url).
CREATE TABLE business_unit (
    id                     UUID PRIMARY KEY,
    main                   BOOLEAN      NOT NULL DEFAULT FALSE,
    trade_name             VARCHAR(255) NOT NULL,
    legal_name             VARCHAR(255),
    document_type          VARCHAR(10),
    document               VARCHAR(20),
    municipal_registration VARCHAR(50),
    phone                  VARCHAR(20),
    whatsapp               VARCHAR(20),
    email                  VARCHAR(255),
    instagram              VARCHAR(100),
    website                VARCHAR(255),
    zip_code               VARCHAR(9),
    street                 VARCHAR(255),
    number                 VARCHAR(20),
    complement             VARCHAR(100),
    district               VARCHAR(100),
    city                   VARCHAR(100),
    state                  VARCHAR(2),
    logo_key               VARCHAR(255),
    logo_url               VARCHAR(500),
    created_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_business_unit_document_type CHECK (document_type IN ('CPF', 'CNPJ'))
);

CREATE UNIQUE INDEX uq_business_unit_main ON business_unit (main) WHERE main;
