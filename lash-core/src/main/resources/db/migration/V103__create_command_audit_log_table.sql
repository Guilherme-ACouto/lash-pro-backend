CREATE TABLE command_audit_log (
    id            UUID PRIMARY KEY,
    command_class VARCHAR(255) NOT NULL,
    payload_json  TEXT,
    user_id       VARCHAR(255),
    executed_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    success       BOOLEAN      NOT NULL
);

CREATE INDEX idx_command_audit_log_command_class ON command_audit_log(command_class);
CREATE INDEX idx_command_audit_log_executed_at ON command_audit_log(executed_at);
