CREATE TABLE collaborator (
    user_id      UUID PRIMARY KEY,
    professional BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE collaborator_permission (
    user_id    UUID         NOT NULL REFERENCES collaborator(user_id) ON DELETE CASCADE,
    permission VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id, permission)
);
