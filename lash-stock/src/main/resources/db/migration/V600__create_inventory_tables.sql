CREATE TABLE inventory_items (
    id                UUID PRIMARY KEY,
    name              VARCHAR(200)   NOT NULL,
    internal_code     VARCHAR(50)    NOT NULL UNIQUE,
    unit              VARCHAR(30)    NOT NULL,
    cost_price        NUMERIC(10,2)  NOT NULL DEFAULT 0,
    supplier          VARCHAR(200),
    current_quantity  NUMERIC(10,3)  NOT NULL DEFAULT 0,
    minimum_quantity  NUMERIC(10,3)  NOT NULL DEFAULT 0,
    active            BOOLEAN        NOT NULL DEFAULT TRUE,
    notes             TEXT,
    created_at        TIMESTAMP      NOT NULL,
    updated_at        TIMESTAMP      NOT NULL
);

CREATE TABLE inventory_movements (
    id                  UUID PRIMARY KEY,
    item_id             UUID           NOT NULL REFERENCES inventory_items(id),
    item_name           VARCHAR(200)   NOT NULL,
    type                VARCHAR(10)    NOT NULL CHECK (type IN ('IN', 'OUT')),
    reason              VARCHAR(20)    NOT NULL CHECK (reason IN ('PURCHASE', 'USAGE', 'LOSS', 'ADJUSTMENT', 'OTHER')),
    quantity            NUMERIC(10,3)  NOT NULL,
    unit_cost           NUMERIC(10,2),
    total_cost          NUMERIC(10,2),
    supplier            VARCHAR(200),
    purchase_date       DATE,
    payment_type        VARCHAR(10)    CHECK (payment_type IN ('CASH', 'INVOICE')),
    due_date            DATE,
    financial_entry_id  UUID,
    notes               TEXT,
    created_at          TIMESTAMP      NOT NULL
);

CREATE INDEX idx_inventory_items_name     ON inventory_items(LOWER(name));
CREATE INDEX idx_inventory_items_code     ON inventory_items(internal_code);
CREATE INDEX idx_inventory_items_active   ON inventory_items(active);
CREATE INDEX idx_inventory_movements_item ON inventory_movements(item_id);
CREATE INDEX idx_inventory_movements_date ON inventory_movements(purchase_date);
