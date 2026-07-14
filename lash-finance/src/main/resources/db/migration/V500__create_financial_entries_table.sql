CREATE TABLE IF NOT EXISTS financial_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    payment_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    appointment_id UUID REFERENCES appointments(id),
    category VARCHAR(100),
    notes TEXT,
    payment_method VARCHAR(50),
    expense_type VARCHAR(20),
    received_from VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_financial_type CHECK (type IN ('INCOME', 'EXPENSE')),
    CONSTRAINT chk_financial_status CHECK (status IN ('PENDING', 'PAID', 'OVERDUE')),
    CONSTRAINT chk_expense_type CHECK (expense_type IN ('FIXED', 'VARIABLE', 'PEOPLE', 'TAX', 'TRANSFER', 'SUPPLY'))
);

CREATE INDEX IF NOT EXISTS idx_financial_due_date ON financial_entries (due_date);
CREATE INDEX IF NOT EXISTS idx_financial_status ON financial_entries (status);
CREATE INDEX IF NOT EXISTS idx_financial_type ON financial_entries (type);
CREATE INDEX IF NOT EXISTS idx_financial_appointment ON financial_entries (appointment_id);
