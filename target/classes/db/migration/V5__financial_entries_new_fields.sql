ALTER TABLE financial_entries
    ADD COLUMN payment_method VARCHAR(50),
    ADD COLUMN expense_type   VARCHAR(20),
    ADD COLUMN received_from  VARCHAR(255);

ALTER TABLE financial_entries
    ADD CONSTRAINT chk_expense_type
        CHECK (expense_type IN ('FIXED', 'VARIABLE', 'PEOPLE', 'TAX', 'TRANSFER'));
