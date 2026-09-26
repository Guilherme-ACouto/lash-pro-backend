-- Carga de demonstração pro Dashboard/Financeiro/Estoque/Agenda do tenant de dev.
-- Todas as datas são relativas a CURRENT_DATE (dia em que o seed roda): 90 dias de histórico + 3 semanas
-- de agenda futura. Os lançamentos seguem o mesmo formato que os fluxos reais gravam
-- (AppointmentFinancialPortImpl ao concluir agendamento, InventoryMovementUseCaseImpl ao registrar compra).

-- Serviços (além dos 4 do changeset 1)
INSERT INTO services (name, description, price, duration_minutes) VALUES
    ('Volume Brasileiro',      'Extensão de cílios com fios em Y, efeito volumoso e leve', 180.00, 150),
    ('Lash Lifting',           'Curvatura e nutrição dos cílios naturais',                  120.00,  60),
    ('Design de Sobrancelhas', 'Design com pinça e linha',                                   45.00,  30),
    ('Design com Henna',       'Design de sobrancelhas com aplicação de henna',              60.00,  45),
    ('Brow Lamination',        'Alinhamento e fixação dos fios da sobrancelha',             130.00,  60),
    ('Hidratação de Cílios',   'Tratamento de nutrição dos fios naturais',                   40.00,  20)
ON CONFLICT (name) DO NOTHING;

-- Clientes novos no último mês (alimenta o crescimento de clientes do Dashboard)
INSERT INTO clients (name, phone, email, birth_date, notes, active, created_at, updated_at) VALUES
    ('Beatriz Albuquerque', '(11) 98311-4052', 'bia.albuquerque@exemplo.com', '1996-02-14', NULL,                                  true, CURRENT_DATE - 26 + TIME '10:20', CURRENT_DATE - 26 + TIME '10:20'),
    ('Rafaela Moura',       '(11) 97204-8816', 'rafa.moura@exemplo.com',      '1990-09-09', 'Indicação da Marina Costa.',          true, CURRENT_DATE - 20 + TIME '15:40', CURRENT_DATE - 20 + TIME '15:40'),
    ('Sabrina Lopes',       '(11) 99632-0147', NULL,                          '2002-03-03', NULL,                                  true, CURRENT_DATE - 13 + TIME '09:05', CURRENT_DATE - 13 + TIME '09:05'),
    ('Helena Vasconcelos',  '(11) 98870-5529', 'helena.vasc@exemplo.com',     '1983-11-30', 'Sobrancelhas com falhas — henna.',    true, CURRENT_DATE - 8  + TIME '11:15', CURRENT_DATE - 8  + TIME '11:15'),
    ('Yasmin Carvalho',     '(11) 97541-3360', 'yasmin.carvalho@exemplo.com', '1999-07-21', NULL,                                  true, CURRENT_DATE - 4  + TIME '17:50', CURRENT_DATE - 4  + TIME '17:50'),
    ('Luana Prado',         '(11) 96318-7702', 'luana.prado@exemplo.com',     '1995-05-12', 'Primeira vez — quer efeito natural.', true, CURRENT_DATE - 1  + TIME '13:30', CURRENT_DATE - 1  + TIME '13:30')
ON CONFLICT (phone) DO NOTHING;

-- Agendamentos: até 4 horários por dia (o último só com serviços curtos), sem domingo, sábado e sexta mais cheios.
-- Hoje sempre com os 4 horários preenchidos, pra agenda do dia no Dashboard.
INSERT INTO appointments (client_id, service_id, scheduled_date, scheduled_time, duration_minutes, status, created_at, updated_at)
SELECT c.id,
       s.id,
       x.d,
       x.t,
       s.duration_minutes,
       CASE
           WHEN x.d < CURRENT_DATE THEN
               CASE WHEN x.r_status < 0.82 THEN 'COMPLETED' WHEN x.r_status < 0.92 THEN 'CANCELLED' ELSE 'NO_SHOW' END
           WHEN x.d = CURRENT_DATE THEN
               CASE x.slot WHEN 1 THEN 'COMPLETED' WHEN 4 THEN 'SCHEDULED' ELSE 'CONFIRMED' END
           WHEN x.d <= CURRENT_DATE + 7 THEN
               CASE WHEN x.r_status < 0.55 THEN 'CONFIRMED' WHEN x.r_status < 0.95 THEN 'SCHEDULED' ELSE 'CANCELLED' END
           ELSE
               CASE WHEN x.r_status < 0.95 THEN 'SCHEDULED' ELSE 'CANCELLED' END
       END,
       LEAST(x.d - 5, CURRENT_DATE) + TIME '09:00',
       LEAST(x.d - 5, CURRENT_DATE) + TIME '09:00'
FROM (
    SELECT g.d::date AS d, sl.slot, sl.t, random() AS r_status, random() AS r_service
    FROM generate_series(CURRENT_DATE - 90, CURRENT_DATE + 21, interval '1 day') g(d)
    CROSS JOIN (VALUES (1, TIME '08:30'), (2, TIME '11:30'), (3, TIME '14:30'), (4, TIME '17:30')) sl(slot, t)
    WHERE g.d::date = CURRENT_DATE
       OR (extract(isodow FROM g.d) <> 7
           AND random() < CASE WHEN extract(isodow FROM g.d) IN (5, 6) THEN 0.95 ELSE 0.80 END)
) x
JOIN services s ON s.name = CASE
    WHEN x.slot = 4 THEN
        CASE WHEN x.r_service < 0.30 THEN 'Design de Sobrancelhas'
             WHEN x.r_service < 0.50 THEN 'Design com Henna'
             WHEN x.r_service < 0.68 THEN 'Lash Lifting'
             WHEN x.r_service < 0.82 THEN 'Brow Lamination'
             WHEN x.r_service < 0.92 THEN 'Retirada'
             ELSE 'Hidratação de Cílios' END
    ELSE
        CASE WHEN x.r_service < 0.30 THEN 'Manutenção'
             WHEN x.r_service < 0.48 THEN 'Volume Russo'
             WHEN x.r_service < 0.62 THEN 'Fio a Fio'
             WHEN x.r_service < 0.74 THEN 'Volume Brasileiro'
             WHEN x.r_service < 0.84 THEN 'Lash Lifting'
             WHEN x.r_service < 0.92 THEN 'Brow Lamination'
             ELSE 'Design com Henna' END
END
CROSS JOIN LATERAL (
    SELECT cl.id
    FROM clients cl
    WHERE cl.active AND cl.created_at::date <= x.d
    ORDER BY random()
    LIMIT 1
) c;

-- Receita dos agendamentos concluídos (igual ao AppointmentFinancialPortImpl)
INSERT INTO financial_entries (type, description, amount, due_date, payment_date, status, appointment_id, category, payment_method, created_at, updated_at)
SELECT 'INCOME',
       s.name || ' — ' || c.name,
       s.price,
       a.scheduled_date,
       a.scheduled_date,
       'PAID',
       a.id,
       'Serviço',
       (ARRAY['PIX', 'PIX', 'PIX', 'Cartão Crédito', 'Cartão Crédito', 'Cartão Débito', 'Dinheiro', 'Transferência'])[1 + floor(random() * 8)::int],
       a.scheduled_date + a.scheduled_time + make_interval(mins => a.duration_minutes),
       a.scheduled_date + a.scheduled_time + make_interval(mins => a.duration_minutes)
FROM appointments a
JOIN services s ON s.id = a.service_id
JOIN clients c ON c.id = a.client_id
WHERE a.status = 'COMPLETED' AND a.financial_entry_id IS NULL;

UPDATE appointments a
SET financial_entry_id = f.id
FROM financial_entries f
WHERE f.appointment_id = a.id AND a.financial_entry_id IS NULL;

-- Venda de produtos de home care (~30% dos dias úteis)
INSERT INTO financial_entries (type, description, amount, due_date, payment_date, status, category, payment_method, created_at, updated_at)
SELECT 'INCOME', p.descr, p.price, g.d::date, g.d::date, 'PAID', 'Venda de produto',
       (ARRAY['PIX', 'Cartão Crédito', 'Cartão Débito', 'Dinheiro'])[1 + floor(random() * 4)::int],
       g.d::date + TIME '18:00', g.d::date + TIME '18:00'
FROM generate_series(CURRENT_DATE - 90, CURRENT_DATE - 1, interval '1 day') g(d)
CROSS JOIN LATERAL (
    SELECT v.descr, v.price
    FROM (VALUES ('Venda: Kit pós-procedimento', 45.00),
                 ('Venda: Shampoo para cílios', 38.00),
                 ('Venda: Sérum de crescimento de cílios', 89.90)) v(descr, price)
    WHERE g.d IS NOT NULL
    ORDER BY random()
    LIMIT 1
) p
WHERE extract(isodow FROM g.d) <> 7 AND random() < 0.30;

-- Gorjetas esporádicas
INSERT INTO financial_entries (type, description, amount, due_date, payment_date, status, category, payment_method, created_at, updated_at)
SELECT 'INCOME', 'Gorjeta', (ARRAY[20.00, 30.00, 50.00])[1 + floor(random() * 3)::int], g.d::date, g.d::date, 'PAID', 'Gorjeta', 'PIX',
       g.d::date + TIME '18:30', g.d::date + TIME '18:30'
FROM generate_series(CURRENT_DATE - 90, CURRENT_DATE - 1, interval '1 day') g(d)
WHERE extract(isodow FROM g.d) <> 7 AND random() < 0.08;

-- Contas a receber (pendentes e vencidas)
INSERT INTO financial_entries (type, description, amount, due_date, payment_date, status, category, payment_method, received_from, created_at, updated_at) VALUES
    ('INCOME', 'Pacote 3 manutenções',                 270.00, CURRENT_DATE + 3,  NULL, 'PENDING', 'Serviço',             'PIX',            'Camila Rocha',              CURRENT_DATE - 10 + TIME '10:00', CURRENT_DATE - 10 + TIME '10:00'),
    ('INCOME', 'Volume Russo — parcela 2/3',            66.67, CURRENT_DATE - 6,  NULL, 'OVERDUE', 'Serviço',             'Cartão Crédito', 'Fernanda Melo',             CURRENT_DATE - 36 + TIME '10:00', CURRENT_DATE - 36 + TIME '10:00'),
    ('INCOME', 'Volume Russo — parcela 3/3',            66.67, CURRENT_DATE + 24, NULL, 'PENDING', 'Serviço',             'Cartão Crédito', 'Fernanda Melo',             CURRENT_DATE - 36 + TIME '10:00', CURRENT_DATE - 36 + TIME '10:00'),
    ('INCOME', 'Curso de lash (aluna) — 2ª parcela',   450.00, CURRENT_DATE + 10, NULL, 'PENDING', 'Outros recebimentos', 'Transferência',  'Aluna particular',          CURRENT_DATE - 20 + TIME '10:00', CURRENT_DATE - 20 + TIME '10:00'),
    ('INCOME', 'Venda de kit a prazo',                  89.90, CURRENT_DATE + 1,  NULL, 'PENDING', 'Venda de produto',    'PIX',            'Gabriela Martins',          CURRENT_DATE - 5  + TIME '10:00', CURRENT_DATE - 5  + TIME '10:00'),
    ('INCOME', 'Comissão parceria loja de cosméticos', 180.00, CURRENT_DATE - 12, NULL, 'OVERDUE', 'Outros recebimentos', 'Transferência',  'Loja Bella Cosméticos',     CURRENT_DATE - 40 + TIME '10:00', CURRENT_DATE - 40 + TIME '10:00');

-- Despesas recorrentes dos 2 meses anteriores + mês atual + próximo mês (a agenda começa 90 dias atrás)
INSERT INTO financial_entries (type, expense_type, description, amount, due_date, payment_date, status, category, payment_method, received_from, created_at, updated_at)
SELECT 'EXPENSE',
       e.expense_type,
       e.descr || ' — ' || to_char(dd.due, 'MM/YYYY'),
       round((e.amount * (1 + (random() - 0.5) * e.var))::numeric, 2),
       dd.due,
       CASE WHEN dd.due < CURRENT_DATE AND NOT dd.overdue THEN dd.due END,
       CASE WHEN dd.overdue THEN 'OVERDUE' WHEN dd.due < CURRENT_DATE THEN 'PAID' ELSE 'PENDING' END,
       e.category,
       e.pm,
       e.counterpart,
       dd.due - 10 + TIME '09:00',
       dd.due - 10 + TIME '09:00'
FROM generate_series(-2, 1) g(m)
CROSS JOIN (VALUES
    ('FIXED',    'Aluguel do estúdio',             'Aluguel',             5,  1500.00, 0.00, 'PIX',            'Imobiliária Central'),
    ('FIXED',    'Internet fibra 500 Mb',          'Internet',            10,  119.90, 0.00, 'Cartão Crédito', 'Vivo Fibra'),
    ('FIXED',    'Plano de celular',               'Telefone',            12,   69.90, 0.00, 'Cartão Crédito', 'Claro'),
    ('FIXED',    'Assinatura Canva Pro',           'Software/Assinatura', 15,   34.90, 0.00, 'Cartão Crédito', 'Canva'),
    ('VARIABLE', 'Conta de energia',               'Energia',             18,  240.00, 0.30, 'PIX',            'Enel'),
    ('VARIABLE', 'Impulsionamento Instagram',      'Marketing',           8,   200.00, 0.60, 'Cartão Crédito', 'Meta'),
    ('VARIABLE', 'Lavanderia (toalhas e lençóis)', 'Outros',              25,   90.00, 0.40, 'PIX',            'Lavanderia Bolha'),
    ('PEOPLE',   'Pró-labore',                     'Pró-labore',          5,  2200.00, 0.00, 'Transferência',  NULL),
    ('PEOPLE',   'Comissão assistente',            'Comissão',            6,   600.00, 0.30, 'PIX',            'Rafaela Nunes'),
    ('TAX',      'DAS — Simples Nacional',         'Simples Nacional',    20,  520.00, 0.25, 'PIX',            'Receita Federal'),
    ('TRANSFER', 'Reserva de emergência',          'Outros',              28,  300.00, 0.00, 'Transferência',  'Conta poupança')
) e(expense_type, descr, category, dia, amount, var, pm, counterpart)
CROSS JOIN LATERAL (
    SELECT due, (g.m = -1 AND e.descr = 'Conta de energia') AS overdue
    FROM (SELECT (date_trunc('month', CURRENT_DATE) + make_interval(months => g.m))::date + (e.dia - 1) AS due) d
) dd;

-- Estoque: itens (quantidade atual = entradas - saídas abaixo, custo = último custo de compra)
INSERT INTO inventory_items (id, name, internal_code, unit, cost_price, supplier, current_quantity, minimum_quantity, active, notes, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Fios Volume Russo 0.07 mix 8–14 mm',         'LSH-001', 'un',  48.00,  'Nagaraku Brasil',           7,   4,   true,  'Bandeja com 16 linhas',          CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Fios Fio a Fio 0.15 curvatura C mix 9–13 mm', 'LSH-002', 'un',  42.00,  'Nagaraku Brasil',           3,   2,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Fios Volume Brasileiro YY 0.07 mix',          'LSH-003', 'un',  55.00,  'Beauty Lash Distribuidora', 2,   3,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Cola para extensão Elite 5 ml',               'LSH-004', 'un',  89.90,  'Distribuidora Belle',       3,   2,   true,  'Validade de 60 dias após aberta', CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Primer para cílios',                          'LSH-005', 'un',  29.90,  'Distribuidora Belle',       4,   2,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Removedor em gel',                            'LSH-006', 'un',  34.90,  'Distribuidora Belle',       2,   2,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Shampoo para cílios (uso no estúdio)',        'LSH-007', 'ml',  0.12,   'Distribuidora Belle',       850, 500, true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Patches de hidrogel',                         'LSH-008', 'par', 0.45,   'Beauty Lash Distribuidora', 140, 100, true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Fita micropore',                              'LSH-009', 'un',  6.50,   'Atacado Saúde',             9,   5,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Microbrush',                                  'LSH-010', 'un',  0.08,   'Atacado Saúde',             380, 200, true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Escovinhas descartáveis',                     'LSH-011', 'un',  0.10,   'Atacado Saúde',             60,  150, true,  'Também vai no kit pós-procedimento', CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Kit Lash Lifting (loções 1, 2 e 3)',          'LSH-012', 'un',  159.00, 'Distribuidora Belle',       0,   1,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Henna para sobrancelhas castanho médio',      'SBR-001', 'g',   2.20,   'Distribuidora Belle',       18,  10,  true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Linha 100% algodão para design',              'SBR-002', 'un',  12.00,  'Atacado Saúde',             3,   2,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Kit Brow Lamination',                         'SBR-003', 'un',  189.00, 'Distribuidora Belle',       1,   1,   true,  NULL,                             CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 120 + TIME '10:00'),
    (gen_random_uuid(), 'Cola Premium (linha descontinuada)',          'LSH-099', 'un',  75.00,  NULL,                        0,   0,   false, 'Fornecedor parou de fabricar',   CURRENT_DATE - 120 + TIME '10:00', CURRENT_DATE - 60 + TIME '10:00')
ON CONFLICT (internal_code) DO NOTHING;

-- Estoque: compras (à vista = CASH, a prazo = INVOICE com vencimento em 30 dias)
INSERT INTO inventory_movements (id, item_id, item_name, type, reason, quantity, unit_cost, total_cost, supplier, purchase_date, payment_type, due_date, financial_entry_id, created_at)
SELECT gen_random_uuid(), i.id, i.name, 'IN', 'PURCHASE', p.qty, p.cost, round(p.qty * p.cost, 2), i.supplier,
       CURRENT_DATE - p.dias,
       p.pt,
       CASE WHEN p.pt = 'INVOICE' THEN CURRENT_DATE - p.dias + 30 END,
       gen_random_uuid(),
       CURRENT_DATE - p.dias + TIME '15:00'
FROM (VALUES
    ('LSH-001', 82, 6,    46.00,  'CASH'),
    ('LSH-001', 35, 6,    48.00,  'INVOICE'),
    ('LSH-002', 70, 4,    42.00,  'CASH'),
    ('LSH-003', 60, 4,    55.00,  'INVOICE'),
    ('LSH-003', 12, 2,    55.00,  'CASH'),
    ('LSH-004', 75, 3,    89.90,  'CASH'),
    ('LSH-004', 20, 2,    89.90,  'INVOICE'),
    ('LSH-005', 50, 4,    29.90,  'CASH'),
    ('LSH-006', 50, 3,    34.90,  'CASH'),
    ('LSH-007', 40, 1000, 0.12,   'CASH'),
    ('LSH-008', 65, 200,  0.45,   'INVOICE'),
    ('LSH-008', 8,  100,  0.45,   'INVOICE'),
    ('LSH-009', 45, 12,   6.50,   'CASH'),
    ('LSH-010', 55, 500,  0.08,   'CASH'),
    ('LSH-011', 88, 300,  0.10,   'CASH'),
    ('LSH-012', 58, 2,    159.00, 'INVOICE'),
    ('SBR-001', 30, 30,   2.20,   'CASH'),
    ('SBR-002', 66, 5,    12.00,  'CASH'),
    ('SBR-003', 25, 2,    189.00, 'INVOICE')
) p(code, dias, qty, cost, pt)
JOIN inventory_items i ON i.internal_code = p.code;

-- Despesa de cada compra (igual ao InventoryMovementUseCaseImpl, e a prazo já vencido entra como paga no vencimento)
INSERT INTO financial_entries (id, type, expense_type, description, amount, due_date, payment_date, status, created_at, updated_at)
SELECT m.financial_entry_id,
       'EXPENSE',
       'SUPPLY',
       'Compra: ' || m.item_name,
       m.total_cost,
       COALESCE(m.due_date, m.purchase_date),
       CASE WHEN m.payment_type = 'CASH' THEN m.purchase_date WHEN m.due_date < CURRENT_DATE THEN m.due_date END,
       CASE WHEN m.payment_type = 'CASH' OR m.due_date < CURRENT_DATE THEN 'PAID' ELSE 'PENDING' END,
       m.created_at,
       m.created_at
FROM inventory_movements m
WHERE m.reason = 'PURCHASE'
  AND m.financial_entry_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM financial_entries f WHERE f.id = m.financial_entry_id);

-- Estoque: saídas (uso, perda, ajuste)
INSERT INTO inventory_movements (id, item_id, item_name, type, reason, quantity, purchase_date, notes, created_at)
SELECT gen_random_uuid(), i.id, i.name, 'OUT', o.reason, o.qty, CURRENT_DATE - o.dias, o.notes, CURRENT_DATE - o.dias + TIME '19:00'
FROM (VALUES
    ('LSH-001', 60, 3,   'USAGE',      NULL),
    ('LSH-001', 20, 2,   'USAGE',      NULL),
    ('LSH-002', 40, 1,   'USAGE',      NULL),
    ('LSH-003', 30, 3,   'USAGE',      NULL),
    ('LSH-003', 5,  1,   'USAGE',      NULL),
    ('LSH-004', 45, 1,   'LOSS',       'Frasco venceu antes de terminar'),
    ('LSH-004', 10, 1,   'USAGE',      NULL),
    ('LSH-006', 15, 1,   'USAGE',      NULL),
    ('LSH-007', 10, 150, 'USAGE',      NULL),
    ('LSH-008', 30, 160, 'USAGE',      NULL),
    ('LSH-009', 20, 3,   'USAGE',      NULL),
    ('LSH-010', 25, 120, 'USAGE',      NULL),
    ('LSH-011', 15, 240, 'USAGE',      NULL),
    ('LSH-012', 14, 2,   'USAGE',      NULL),
    ('SBR-001', 7,  12,  'USAGE',      NULL),
    ('SBR-002', 40, 2,   'ADJUSTMENT', 'Contagem de inventário'),
    ('SBR-003', 9,  1,   'USAGE',      NULL)
) o(code, dias, qty, reason, notes)
JOIN inventory_items i ON i.internal_code = o.code;
