-- Campos faltantes em inventory_items
ALTER TABLE inventory_items
    ADD COLUMN internal_code VARCHAR(50),
    ADD COLUMN cost_price    NUMERIC(10,2) NOT NULL DEFAULT 0,
    ADD COLUMN supplier      VARCHAR(255),
    ADD COLUMN notes         TEXT;

ALTER TABLE inventory_items
    ADD CONSTRAINT uq_inventory_code UNIQUE (internal_code);

-- Enriquecer inventory_movements com campos de compra
ALTER TABLE inventory_movements
    ADD COLUMN reason            VARCHAR(20) NOT NULL DEFAULT 'ADJUSTMENT',
    ADD COLUMN unit_cost         NUMERIC(10,2),
    ADD COLUMN supplier          VARCHAR(255),
    ADD COLUMN purchase_date     DATE,
    ADD COLUMN payment_type      VARCHAR(10),
    ADD COLUMN due_date          DATE,
    ADD COLUMN financial_entry_id UUID REFERENCES financial_entries(id);

ALTER TABLE inventory_movements
    ADD CONSTRAINT chk_movement_reason
        CHECK (reason IN ('PURCHASE','USAGE','LOSS','ADJUSTMENT','OTHER'));

ALTER TABLE inventory_movements
    ADD CONSTRAINT chk_payment_type
        CHECK (payment_type IN ('CASH','INVOICE') OR payment_type IS NULL);

-- Adicionar SUPPLY ao tipo de despesa financeira
ALTER TABLE financial_entries DROP CONSTRAINT IF EXISTS chk_expense_type;
ALTER TABLE financial_entries
    ADD CONSTRAINT chk_expense_type
        CHECK (expense_type IN ('FIXED','VARIABLE','PEOPLE','TAX','TRANSFER','SUPPLY'));

CREATE INDEX idx_inventory_movements_item ON inventory_movements(item_id);
CREATE INDEX idx_inventory_movements_date ON inventory_movements(purchase_date);
