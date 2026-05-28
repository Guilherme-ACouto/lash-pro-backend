INSERT INTO users (id, name, email, password, role) VALUES (
    gen_random_uuid(),
    'Administradora',
    'admin@lashmanager.com',
    '$2a$10$Uz8wcxUcdoCF6ydtQsYe5enPYKYclzDcr3.aWpVy5AO/cQur0g.HO',
    'OWNER'
) ON CONFLICT (email) DO NOTHING;

INSERT INTO services (id, name, description, price, duration_minutes) VALUES
    (gen_random_uuid(), 'Volume Russo', 'Extensão de cílios técnica volume russo', 200.00, 180),
    (gen_random_uuid(), 'Fio a Fio', 'Extensão de cílios clássica fio a fio', 150.00, 120),
    (gen_random_uuid(), 'Manutenção', 'Manutenção de extensão de cílios', 100.00, 90),
    (gen_random_uuid(), 'Retirada', 'Retirada de extensão de cílios', 50.00, 30)
ON CONFLICT DO NOTHING;