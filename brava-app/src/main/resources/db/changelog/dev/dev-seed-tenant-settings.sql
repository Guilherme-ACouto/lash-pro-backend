-- Recepção: agenda e clientes completos, só consulta de serviços e fichas. Sem financeiro/estoque/dashboard.
-- Profissional: atende clientes (aparece como profissional), agenda e clientes sem excluir, fichas completas.
INSERT INTO collaborator (user_id, professional) VALUES
    ('de000000-0000-0000-0000-0000000000a1', FALSE),
    ('de000000-0000-0000-0000-0000000000a2', TRUE)
ON CONFLICT (user_id) DO UPDATE SET professional = EXCLUDED.professional;

INSERT INTO collaborator_permission (user_id, permission) VALUES
    ('de000000-0000-0000-0000-0000000000a1', 'appointment'),
    ('de000000-0000-0000-0000-0000000000a1', 'appointment.create'),
    ('de000000-0000-0000-0000-0000000000a1', 'appointment.update'),
    ('de000000-0000-0000-0000-0000000000a1', 'appointment.delete'),
    ('de000000-0000-0000-0000-0000000000a1', 'client'),
    ('de000000-0000-0000-0000-0000000000a1', 'client.create'),
    ('de000000-0000-0000-0000-0000000000a1', 'client.update'),
    ('de000000-0000-0000-0000-0000000000a1', 'client.delete'),
    ('de000000-0000-0000-0000-0000000000a1', 'service'),
    ('de000000-0000-0000-0000-0000000000a1', 'record'),
    ('de000000-0000-0000-0000-0000000000a2', 'appointment'),
    ('de000000-0000-0000-0000-0000000000a2', 'appointment.create'),
    ('de000000-0000-0000-0000-0000000000a2', 'appointment.update'),
    ('de000000-0000-0000-0000-0000000000a2', 'client'),
    ('de000000-0000-0000-0000-0000000000a2', 'client.create'),
    ('de000000-0000-0000-0000-0000000000a2', 'client.update'),
    ('de000000-0000-0000-0000-0000000000a2', 'service'),
    ('de000000-0000-0000-0000-0000000000a2', 'inventory'),
    ('de000000-0000-0000-0000-0000000000a2', 'record'),
    ('de000000-0000-0000-0000-0000000000a2', 'record.create'),
    ('de000000-0000-0000-0000-0000000000a2', 'record.update'),
    ('de000000-0000-0000-0000-0000000000a2', 'record.delete')
ON CONFLICT DO NOTHING;

-- CNPJ de teste com dígitos verificadores válidos.
UPDATE business_unit SET
    legal_name    = 'Estúdio Dev Beleza e Estética LTDA',
    document_type = 'CNPJ',
    document      = '11222333000181',
    phone         = '(11) 3456-7890',
    whatsapp      = '(11) 98765-4321',
    email         = 'contato@estudiodev.com.br',
    instagram     = '@estudiodev',
    zip_code      = '01310-100',
    street        = 'Avenida Paulista',
    number        = '1000',
    complement    = 'Sala 42',
    district      = 'Bela Vista',
    city          = 'São Paulo',
    state         = 'SP',
    updated_at    = NOW()
WHERE main;
