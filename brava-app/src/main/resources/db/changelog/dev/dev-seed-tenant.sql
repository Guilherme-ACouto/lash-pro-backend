INSERT INTO services (name, description, price, duration_minutes) VALUES
    ('Volume Russo', 'Extensão de cílios técnica volume russo', 200.00, 180),
    ('Fio a Fio',    'Extensão de cílios clássica fio a fio',   150.00, 120),
    ('Manutenção',   'Manutenção de extensão de cílios',         100.00,  90),
    ('Retirada',     'Retirada de extensão de cílios',            50.00,  30)
ON CONFLICT (name) DO NOTHING;

INSERT INTO clients (name, phone, email, birth_date, notes, active, created_at, updated_at) VALUES
    ('Ana Beatriz Souza',  '(11) 98745-2210', 'ana.souza@exemplo.com',        '1994-04-17', 'Prefere acabamento leve, evitar excesso de adesivo no canto interno.', true,  '2026-03-12 10:00', '2026-03-12 10:00'),
    ('Camila Rocha',       '(11) 99620-1187', 'camila.rocha@exemplo.com',     '1990-08-02', 'Volume brasileiro. Retenção média de 21 dias.',                         true,  '2026-03-20 14:30', '2026-03-20 14:30'),
    ('Juliana Alves',      '(11) 98105-7732', 'ju.alves@exemplo.com',         '1997-11-25', NULL,                                                                   true,  '2026-04-02 09:15', '2026-04-02 09:15'),
    ('Bruna Lima',         '(11) 99254-9012', 'bruna.lima@exemplo.com',       '1992-01-30', 'Sensibilidade leve (lacrimejamento). Usar pads de silicone.',           false, '2026-04-10 16:00', '2026-06-01 11:00'),
    ('Fernanda Melo',      '(11) 98831-4420', 'fer.melo@exemplo.com',         '1988-06-14', 'Manutenção a cada 15 dias.',                                           true,  '2026-04-18 11:45', '2026-04-18 11:45'),
    ('Larissa Campos',     '(11) 97412-3308', 'larissa.campos@exemplo.com',   '1999-02-09', 'Primeira extensão — explicar cuidados pós-procedimento.',              true,  '2026-05-03 10:30', '2026-05-03 10:30'),
    ('Patrícia Nogueira',  '(11) 96587-1142', NULL,                           '1985-09-21', 'Alergia a látex.',                                                     true,  '2026-05-07 15:00', '2026-05-07 15:00'),
    ('Renata Figueiredo',  '(11) 98233-6675', 'renata.fig@exemplo.com',       NULL,         NULL,                                                                   true,  '2026-05-15 13:20', '2026-05-15 13:20'),
    ('Gabriela Martins',   '(11) 99471-2089', 'gabi.martins@exemplo.com',     '2001-12-05', 'Gosta de efeito gatinho, curvatura D.',                                true,  '2026-05-22 17:10', '2026-05-22 17:10'),
    ('Isabela Ferraz',     '(11) 97756-4431', 'isabela.ferraz@exemplo.com',   '1995-03-28', NULL,                                                                   true,  '2026-06-04 09:00', '2026-06-04 09:00'),
    ('Marina Costa',       '(11) 98019-5563', 'marina.costa@exemplo.com',     '1993-07-11', 'Lash lifting a cada 2 meses.',                                         true,  '2026-06-12 14:00', '2026-06-12 14:00'),
    ('Letícia Barros',     '(11) 96644-7890', NULL,                           NULL,         'Prefere horários no fim da tarde.',                                    false, '2026-06-19 18:00', '2026-08-10 10:00'),
    ('Vanessa Duarte',     '(11) 99388-2214', 'vanessa.duarte@exemplo.com',   '1987-10-03', 'Usa lentes de contato — remover antes do procedimento.',             true,  '2026-06-27 10:45', '2026-06-27 10:45'),
    ('Thaís Monteiro',     '(11) 98567-9021', 'thais.monteiro@exemplo.com',   '1998-05-19', NULL,                                                                   true,  '2026-07-05 11:30', '2026-07-05 11:30'),
    ('Carolina Pires',     '(11) 97123-6654', 'carol.pires@exemplo.com',      '1991-01-08', 'Volume egípcio.',                                                      true,  '2026-07-14 15:15', '2026-07-14 15:15'),
    ('Aline Ribeiro',      '(11) 99902-3317', 'aline.ribeiro@exemplo.com',    '1996-08-27', 'Gestante — aguardar liberação médica.',                                false, '2026-07-21 09:40', '2026-09-01 09:00'),
    ('Débora Castro',      '(11) 98476-1108', NULL,                           '1989-04-04', NULL,                                                                   true,  '2026-08-02 16:20', '2026-08-02 16:20'),
    ('Priscila Andrade',   '(11) 96215-8843', 'priscila.andrade@exemplo.com', '1994-12-16', 'Fios 0.07, comprimentos 9 a 12.',                                      true,  '2026-08-15 13:00', '2026-08-15 13:00'),
    ('Natália Siqueira',   '(11) 97890-4476', 'natalia.siqueira@exemplo.com', '2000-06-30', 'Indicação da Camila Rocha.',                                           true,  '2026-09-03 10:10', '2026-09-03 10:10'),
    ('Mariana Teixeira',   '(11) 99145-2290', 'mari.teixeira@exemplo.com',    '1992-10-22', NULL,                                                                   true,  '2026-09-18 14:50', '2026-09-18 14:50')
ON CONFLICT (phone) DO NOTHING;
