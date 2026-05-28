ALTER TABLE appointments ADD COLUMN financial_entry_id UUID REFERENCES financial_entries(id);
