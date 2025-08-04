DROP DATABASE kindact;

CREATE DATABASE kindact;

USE kindact;

CREATE TABLE
    tb_ong (
        ong_id INT AUTO_INCREMENT PRIMARY KEY,
        ong_nome VARCHAR(100),
        ong_cnpj VARCHAR(18),
        ong_telefone VARCHAR(20),
        ong_email VARCHAR(100),
        ong_site VARCHAR(255),
        ong_area_atuacao VARCHAR(100),
        ong_nome_responsavel VARCHAR(100),
        ong_endereco VARCHAR(150),
        ong_cep VARCHAR(10),
        ong_logradouro VARCHAR(100),
        ong_numero VARCHAR(10),
        ong_bairro VARCHAR(50),
        ong_complemento VARCHAR(50),
        ong_cidade VARCHAR(50),
        ong_uf CHAR(2)
    );

CREATE TABLE
    tb_voluntario (
        voluntario_id INT AUTO_INCREMENT PRIMARY KEY,
        voluntario_nome VARCHAR(100),
        voluntario_cpf VARCHAR(14),
        voluntario_data_nascimento DATE,
        voluntario_idade INT,
        voluntario_sexo VARCHAR(10),
        voluntario_profissao VARCHAR(50),
        voluntario_telefone VARCHAR(20),
        voluntario_email VARCHAR(100),
        voluntario_endereco VARCHAR(150),
        voluntario_cep VARCHAR(10),
        voluntario_logradouro VARCHAR(100),
        voluntario_numero VARCHAR(10),
        voluntario_bairro VARCHAR(50),
        voluntario_complemento VARCHAR(50),
        voluntario_cidade VARCHAR(50),
        voluntario_uf CHAR(2)
    );

CREATE TABLE
    tb_evento (
        evento_id INT AUTO_INCREMENT PRIMARY KEY,
        evento_titulo VARCHAR(100),
        evento_descricao TEXT,
        evento_data_inicio DATE,
        evento_data_termino DATE,
        evento_endereco VARCHAR(150),
        evento_cep VARCHAR(10),
        evento_logradouro VARCHAR(100),
        evento_numero VARCHAR(10),
        evento_bairro VARCHAR(50),
        evento_complemento VARCHAR(50),
        evento_cidade VARCHAR(50),
        evento_uf CHAR(2),
        fk_ong_id INT,
        FOREIGN KEY (fk_ong_id) REFERENCES tb_ong (ong_id)
    );

CREATE TABLE
    tb_destinatario (
        destinatario_id INT AUTO_INCREMENT PRIMARY KEY,
        destinatario_nome VARCHAR(100),
        destinatario_endereco VARCHAR(150),
        destinatario_cep VARCHAR(10),
        destinatario_logradouro VARCHAR(100),
        destinatario_numero VARCHAR(10),
        destinatario_bairro VARCHAR(50),
        destinatario_complemento VARCHAR(50),
        destinatario_cidade VARCHAR(50),
        destinatario_uf CHAR(2),
        destinatario_telefone VARCHAR(20)
    );

CREATE TABLE
    tb_doacao (
        doacao_id INT AUTO_INCREMENT PRIMARY KEY,
        doacao_tipo VARCHAR(50),
        doacao_valor DECIMAL(10, 2),
        doacao_mensagem TEXT,
        doacao_data DATE,
        fk_voluntario_id INT,
        fk_ong_id INT,
        fk_destinatario_id INT,
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario (voluntario_id),
        FOREIGN KEY (fk_ong_id) REFERENCES tb_ong (ong_id),
        FOREIGN KEY (fk_destinatario_id) REFERENCES tb_destinatario (destinatario_id)
    );

CREATE TABLE
    tb_curadoria (
        curadoria_id INT AUTO_INCREMENT PRIMARY KEY,
        curadoria_nome VARCHAR(100),
        curadoria_requisitos_minimos TEXT,
        fk_ong_id INT,
        FOREIGN KEY (fk_ong_id) REFERENCES tb_ong (ong_id)
    );

CREATE TABLE
    tb_cargos (
        cargo_id INT AUTO_INCREMENT PRIMARY KEY,
        cargo_nome VARCHAR(100),
        cargo_descricao TEXT
    );

CREATE TABLE
    tb_voluntario_evento (
        fk_voluntario_id INT,
        fk_evento_id INT,
        PRIMARY KEY (fk_voluntario_id, fk_evento_id),
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario (voluntario_id),
        FOREIGN KEY (fk_evento_id) REFERENCES tb_evento (evento_id)
    );

CREATE TABLE
    tb_voluntario_curadoria (
        fk_voluntario_id INT,
        fk_curadoria_id INT,
        voluntario_curadoria_status_aprovacao VARCHAR(50),
        PRIMARY KEY (fk_voluntario_id, fk_curadoria_id),
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario (voluntario_id),
        FOREIGN KEY (fk_curadoria_id) REFERENCES tb_curadoria (curadoria_id)
    );

CREATE TABLE
    tb_avaliacao (
        avaliacao_id INT AUTO_INCREMENT PRIMARY KEY,
        avaliacao_nota INT,
        avaliacao_comentario TEXT,
        fk_voluntario_id INT,
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario (voluntario_id)
    );

CREATE TABLE
    tb_voluntario_cargo (
        fk_voluntario_id INT,
        fk_cargo_id INT,
        PRIMARY KEY (fk_voluntario_id, fk_cargo_id),
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario (voluntario_id),
        FOREIGN KEY (fk_cargo_id) REFERENCES tb_cargos (cargo_id)
    );

CREATE TABLE 
    tb_mensagem (
        mensagem_id INT PRIMARY KEY AUTO_INCREMENT,
        fk_voluntario_id INT,
        fk_ong_id INT,
        mensagem_conteudo TEXT,
        mensagem_data_envio DATETIME DEFAULT CURRENT_TIMESTAMP,  
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario(voluntario_id),
        FOREIGN KEY (fk_ong_id) REFERENCES tb_ong(ong_id)
    );

CREATE TABLE
    tb_notificacao (
        notificacao_id INT PRIMARY KEY AUTO_INCREMENT,
        fk_voluntario_id INT,
        notificacao_msg TEXT,
        notificacao_data DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (fk_voluntario_id) REFERENCES tb_voluntario(voluntario_id)
    );

INSERT INTO
    tb_ong (
        ong_nome,
        ong_cnpj,
        ong_telefone,
        ong_email,
        ong_site,
        ong_area_atuacao,
        ong_nome_responsavel,
        ong_endereco,
        ong_cep,
        ong_logradouro,
        ong_numero,
        ong_bairro,
        ong_complemento,
        ong_cidade,
        ong_uf
    )
VALUES
    (
        'Instituto Esperança',
        '12.345.678/0001-90',
        '(11) 91234-5678',
        'contato@institutoesperanca.org',
        'www.institutoesperanca.org',
        'Assistência Social',
        'Maria Silva',
        'Rua das Flores, 300, São Paulo',
        '01001-000',
        'Rua das Flores',
        '300',
        'Centro',
        '',
        'São Paulo',
        'SP'
    ),
    (
        'Saúde para Todos',
        '98.765.432/0001-12',
        '(21) 92345-6789',
        'contato@saudeparatodos.org',
        'www.saudeparatodos.org',
        'Saúde',
        'Carlos Souza',
        'Av. Brasil, 200, Rio de Janeiro',
        '20000-000',
        'Av. Brasil',
        '200',
        'Copacabana',
        'Apto 101',
        'Rio de Janeiro',
        'RJ'
    ),
    (
        'Crescer Bem',
        '11.222.333/0001-44',
        '(31) 91111-2222',
        'contato@crescerbem.org',
        'www.crescerbem.org',
        'Educação',
        'Bruna Lima',
        'Rua da Paz, 10, Belo Horizonte',
        '30100-000',
        'Rua da Paz',
        '10',
        'Centro',
        '',
        'Belo Horizonte',
        'MG'
    ),
    (
        'Arte e Vida',
        '22.333.444/0001-55',
        '(41) 92222-3333',
        'contato@arteevida.org',
        'www.arteevida.org',
        'Cultura',
        'Eduardo Ramos',
        'Av. União, 200, Curitiba',
        '80000-000',
        'Av. União',
        '200',
        'Batel',
        '',
        'Curitiba',
        'PR'
    ),
    (
        'Juventude Ativa',
        '33.444.555/0001-66',
        '(51) 93333-4444',
        'contato@juventudeativa.org',
        'www.juventudeativa.org',
        'Juventude',
        'Fernanda Costa',
        'Rua Esperança, 300, Porto Alegre',
        '90000-000',
        'Rua Esperança',
        '300',
        'Centro',
        '',
        'Porto Alegre',
        'RS'
    ),
    (
        'Verde Limpo',
        '44.555.666/0001-77',
        '(61) 94444-5555',
        'contato@verdelimpo.org',
        'www.verdelimpo.org',
        'Meio Ambiente',
        'Gabriel Souza',
        'Av. das Luzes, 400, Brasília',
        '70000-000',
        'Av. das Luzes',
        '400',
        'Asa Sul',
        '',
        'Brasília',
        'DF'
    ),
    (
        'Vacina Já',
        '55.666.777/0001-88',
        '(71) 95555-6666',
        'contato@vacinaja.org',
        'www.vacinaja.org',
        'Saúde',
        'Helena Martins',
        'Rua Nova, 500, Salvador',
        '40000-000',
        'Rua Nova',
        '500',
        'Barra',
        '',
        'Salvador',
        'BA'
    ),
    (
        'Futuro Solidário',
        '66.777.888/0001-99',
        '(81) 96666-7777',
        'contato@futurosolidario.org',
        'www.futurosolidario.org',
        'Assistência Social',
        'Igor Pereira',
        'Av. Futuro, 600, Recife',
        '50000-000',
        'Av. Futuro',
        '600',
        'Boa Viagem',
        '',
        'Recife',
        'PE'
    );


INSERT INTO
    tb_voluntario (
        voluntario_nome,
        voluntario_cpf,
        voluntario_data_nascimento,
        voluntario_idade,
        voluntario_sexo,
        voluntario_profissao,
        voluntario_telefone,
        voluntario_email,
        voluntario_endereco,
        voluntario_cep,
        voluntario_logradouro,
        voluntario_numero,
        voluntario_bairro,
        voluntario_complemento,
        voluntario_cidade,
        voluntario_uf
    )
VALUES
    (
        'Ana Paula',
        '123.456.789-00',
        '1990-05-10',
        34,
        'Feminino',
        'Enfermeira',
        '(11) 91234-5678',
        'ana.paula@email.com',
        'Rua das Flores, 50, São Paulo',
        '01001-000',
        'Rua das Flores',
        '50',
        'Centro',
        '',
        'São Paulo',
        'SP'
    ),
    (
        'Carlos Mendes',
        '987.654.321-11',
        '1985-08-22',
        38,
        'Masculino',
        'Professor',
        '(21) 92345-6789',
        'carlos.mendes@email.com',
        'Av. Brasil, 150, Rio de Janeiro',
        '20000-000',
        'Av. Brasil',
        '150',
        'Copacabana',
        'Apto 202',
        'Rio de Janeiro',
        'RJ'
    ),
    (
        'Bruna Silva',
        '111.222.333-44',
        '1992-03-15',
        32,
        'Feminino',
        'Psicóloga',
        '(31) 91111-2222',
        'bruna.silva@email.com',
        'Rua da Paz, 20, Belo Horizonte',
        '30100-000',
        'Rua da Paz',
        '20',
        'Centro',
        '',
        'Belo Horizonte',
        'MG'
    ),
    (
        'Eduardo Ramos',
        '222.333.444-55',
        '1988-07-20',
        36,
        'Masculino',
        'Engenheiro',
        '(41) 92222-3333',
        'eduardo.ramos@email.com',
        'Av. União, 80, Curitiba',
        '80000-000',
        'Av. União',
        '80',
        'Batel',
        '',
        'Curitiba',
        'PR'
    ),
    (
        'Fernanda Costa',
        '333.444.555-66',
        '1995-11-05',
        29,
        'Feminino',
        'Advogada',
        '(51) 93333-4444',
        'fernanda.costa@email.com',
        'Rua Esperança, 150, Porto Alegre',
        '90000-000',
        'Rua Esperança',
        '150',
        'Centro',
        '',
        'Porto Alegre',
        'RS'
    ),
    (
        'Gabriel Souza',
        '444.555.666-77',
        '1991-01-30',
        33,
        'Masculino',
        'Médico',
        '(61) 94444-5555',
        'gabriel.souza@email.com',
        'Av. das Luzes, 250, Brasília',
        '70000-000',
        'Av. das Luzes',
        '250',
        'Asa Sul',
        '',
        'Brasília',
        'DF'
    ),
    (
        'Helena Martins',
        '555.666.777-88',
        '1993-09-12',
        31,
        'Feminino',
        'Professora',
        '(71) 95555-6666',
        'helena.martins@email.com',
        'Rua Nova, 300, Salvador',
        '40000-000',
        'Rua Nova',
        '300',
        'Barra',
        '',
        'Salvador',
        'BA'
    ),
    (
        'Igor Pereira',
        '666.777.888-99',
        '1987-06-18',
        37,
        'Masculino',
        'Administrador',
        '(81) 96666-7777',
        'igor.pereira@email.com',
        'Av. Futuro, 400, Recife',
        '50000-000',
        'Av. Futuro',
        '400',
        'Boa Viagem',
        '',
        'Recife',
        'PE'
    );

INSERT INTO
    tb_evento (
        evento_titulo,
        evento_descricao,
        evento_data_inicio,
        evento_data_termino,
        evento_endereco,
        evento_cep,
        evento_logradouro,
        evento_numero,
        evento_bairro,
        evento_complemento,
        evento_cidade,
        evento_uf,
        fk_ong_id
    )
VALUES
    (
        'Campanha do Agasalho',
        'Arrecadação de roupas para o inverno',
        '2024-06-01',
        '2024-06-30',
        'Rua das Flores, 300, São Paulo',
        '01001-000',
        'Rua das Flores',
        '300',
        'Centro',
        '',
        'São Paulo',
        'SP',
        1
    ),
    (
        'Mutirão da Saúde',
        'Atendimento médico gratuito',
        '2024-07-10',
        '2024-07-12',
        'Av. Brasil, 200, Rio de Janeiro',
        '20000-000',
        'Av. Brasil',
        '200',
        'Copacabana',
        'Apto 101',
        'Rio de Janeiro',
        'RJ',
        2
    ),
    (
        'Feira da Saúde',
        'Ações de saúde preventiva',
        '2024-08-01',
        '2024-08-02',
        'Rua da Paz, 10, Belo Horizonte',
        '30100-000',
        'Rua da Paz',
        '10',
        'Centro',
        '',
        'Belo Horizonte',
        'MG',
        3
    ),
    (
        'Oficina de Artes',
        'Atividades culturais para crianças',
        '2024-09-10',
        '2024-09-12',
        'Av. União, 200, Curitiba',
        '80000-000',
        'Av. União',
        '200',
        'Batel',
        '',
        'Curitiba',
        'PR',
        4
    ),
    (
        'Palestra Motivacional',
        'Palestra para jovens',
        '2024-10-05',
        '2024-10-05',
        'Rua Esperança, 300, Porto Alegre',
        '90000-000',
        'Rua Esperança',
        '300',
        'Centro',
        '',
        'Porto Alegre',
        'RS',
        5
    ),
    (
        'Mutirão de Limpeza',
        'Limpeza de praças públicas',
        '2024-11-15',
        '2024-11-15',
        'Av. das Luzes, 400, Brasília',
        '70000-000',
        'Av. das Luzes',
        '400',
        'Asa Sul',
        '',
        'Brasília',
        'DF',
        6
    ),
    (
        'Campanha de Vacinação',
        'Vacinação gratuita',
        '2024-12-01',
        '2024-12-02',
        'Rua Nova, 500, Salvador',
        '40000-000',
        'Rua Nova',
        '500',
        'Barra',
        '',
        'Salvador',
        'BA',
        7
    ),
    (
        'Ação Solidária',
        'Distribuição de alimentos',
        '2025-01-20',
        '2025-01-20',
        'Av. Futuro, 600, Recife',
        '50000-000',
        'Av. Futuro',
        '600',
        'Boa Viagem',
        '',
        'Recife',
        'PE',
        8
    ),
    (
        'Encontro de Voluntários',
        'Integração dos voluntários',
        '2025-02-10',
        '2025-02-10',
        'Rua Ação, 700, Fortaleza',
        '60000-000',
        'Rua Ação',
        '700',
        'Meireles',
        '',
        'Fortaleza',
        'CE',
        1
    ),
    (
        'Festival da Criança',
        'Atividades recreativas',
        '2025-03-15',
        '2025-03-16',
        'Av. Crescer, 800, Goiânia',
        '74000-000',
        'Av. Crescer',
        '800',
        'Setor Bueno',
        '',
        'Goiânia',
        'GO',
        2
    );

INSERT INTO
    tb_destinatario (
        destinatario_nome,
        destinatario_endereco,
        destinatario_telefone
    )
VALUES
    (
        'Lar das Crianças',
        'Rua Esperança, 50, São Paulo',
        '(11) 93456-7890'
    ),
    (
        'Casa do Idoso',
        'Av. Alegria, 200, Rio de Janeiro',
        '(21) 94567-8901'
    ),
    (
        'Abrigo Esperança',
        'Rua Alegria, 100, Belo Horizonte',
        '(31) 91234-5678'
    ),
    (
        'Casa da Paz',
        'Av. União, 200, Curitiba',
        '(41) 92345-6789'
    ),
    (
        'Lar Feliz',
        'Rua Esperança, 300, Porto Alegre',
        '(51) 93456-7890'
    ),
    (
        'Centro de Apoio',
        'Av. das Luzes, 400, Brasília',
        '(61) 94567-8901'
    ),
    (
        'Instituto Vida',
        'Rua Nova, 500, Salvador',
        '(71) 95678-9012'
    ),
    (
        'Associação Futuro',
        'Av. Futuro, 600, Recife',
        '(81) 96789-0123'
    ),
    (
        'Projeto Solidariedade',
        'Rua Ação, 700, Fortaleza',
        '(85) 97890-1234'
    ),
    (
        'Crescer Feliz',
        'Av. Crescer, 800, Goiânia',
        '(62) 98901-2345'
    );

INSERT INTO
    tb_doacao (
        doacao_tipo,
        doacao_valor,
        doacao_mensagem,
        doacao_data,
        fk_voluntario_id,
        fk_ong_id,
        fk_destinatario_id
    )
VALUES
    (
        'Dinheiro',
        500.00,
        'Para ajudar nas despesas',
        '2024-06-05',
        1,
        1,
        1
    ),
    (
        'Alimentos',
        0.00,
        'Doação de cestas básicas',
        '2024-06-15',
        2,
        2,
        2
    ),
    (
        'Roupas',
        0.00,
        'Doação de roupas de inverno',
        '2024-08-03',
        3,
        3,
        3
    ),
    (
        'Dinheiro',
        200.00,
        'Apoio financeiro',
        '2024-09-11',
        4,
        4,
        4
    ),
    (
        'Alimentos',
        0.00,
        'Cestas básicas',
        '2024-10-06',
        5,
        5,
        5
    ),
    (
        'Livros',
        0.00,
        'Livros para biblioteca',
        '2024-11-16',
        6,
        6,
        6
    ),
    (
        'Brinquedos',
        0.00,
        'Brinquedos para crianças',
        '2024-12-03',
        7,
        7,
        7
    ),
    (
        'Dinheiro',
        150.00,
        'Ajuda para despesas',
        '2025-01-21',
        8,
        8,
        8
    ),
    (
        'Dinheiro',
        300.00,
        'Doação para projetos',
        '2025-02-11',
        1,
        1,
        1
    ),
    (
        'Alimentos',
        0.00,
        'Doação de alimentos',
        '2025-03-17',
        2,
        2,
        2
    );

INSERT INTO
    tb_curadoria (
        curadoria_nome,
        curadoria_requisitos_minimos,
        fk_ong_id
    )
VALUES
    (
        'Curadoria de Projetos',
        'Experiência em gestão de projetos sociais',
        1
    ),
    (
        'Curadoria de Saúde',
        'Formação na área da saúde',
        2
    ),
    (
        'Curadoria de Cultura',
        'Experiência em projetos culturais',
        3
    ),
    (
        'Curadoria de Educação',
        'Formação em pedagogia',
        4
    ),
    (
        'Curadoria de Saúde Mental',
        'Psicologia ou áreas afins',
        5
    ),
    (
        'Curadoria de Esportes',
        'Experiência em esportes',
        6
    ),
    (
        'Curadoria de Meio Ambiente',
        'Conhecimento em sustentabilidade',
        7
    ),
    (
        'Curadoria de Tecnologia',
        'Formação em TI',
        8
    ),
    (
        'Curadoria de Comunicação',
        'Experiência em comunicação social',
        1
    ),
    (
        'Curadoria de Finanças',
        'Formação em contabilidade',
        2
    );

INSERT INTO
    tb_cargos (
        cargo_nome,
        cargo_descricao
    )
VALUES
    (
        'Coordenador',
        'Responsável pela coordenação das atividades'
    ),
    (
        'Assistente',
        'Auxilia nas tarefas diárias'
    ),
    (
        'Tesoureiro',
        'Responsável pelas finanças'
    ),
    (
        'Secretário',
        'Organiza documentos e reuniões'
    ),
    (
        'Instrutor',
        'Ministra oficinas e cursos'
    ),
    (
        'Divulgador',
        'Responsável pela divulgação'
    ),
    (
        'Motorista',
        'Responsável pelo transporte'
    ),
    (
        'Assistente Social',
        'Atende famílias e pessoas assistidas'
    ),
    (
        'Coordenador de Projetos',
        'Coordena projetos sociais'
    ),
    (
        'Analista de Dados',
        'Analisa dados das ações'
    );

INSERT INTO
    tb_voluntario_evento (
        fk_voluntario_id,
        fk_evento_id
    )
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (1, 3),
    (2, 4);

INSERT INTO
    tb_voluntario_curadoria (
        fk_voluntario_id,
        fk_curadoria_id,
        voluntario_curadoria_status_aprovacao
    )
VALUES
    (1, 1, 'Aprovado'),
    (2, 2, 'Pendente'),
    (3, 3, 'Aprovado'),
    (4, 4, 'Pendente'),
    (5, 5, 'Aprovado'),
    (6, 6, 'Pendente'),
    (7, 7, 'Aprovado'),
    (8, 8, 'Pendente'),
    (1, 5, 'Aprovado'),
    (2, 6, 'Pendente');

INSERT INTO
    tb_avaliacao (
        avaliacao_nota,
        avaliacao_comentario,
        fk_voluntario_id
    )
VALUES
    (5, 'Excelente trabalho!', 1),
    (4, 'Muito dedicado.', 2),
    (3, 'Bom trabalho.', 3),
    (5, 'Excelente!', 4),
    (4, 'Muito participativo.', 5),
    (2, 'Precisa melhorar.', 6),
    (5, 'Ótima atuação.', 7),
    (4, 'Dedicado.', 8),
    (5, 'Sempre presente.', 1),
    (3, 'Atencioso.', 2);

INSERT INTO
    tb_voluntario_cargo (
        fk_voluntario_id,
        fk_cargo_id
    )
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (1, 5),
    (2, 6);

INSERT INTO
    tb_mensagem (
        fk_voluntario_id,
        fk_ong_id,
        mensagem_conteudo,
        mensagem_data_envio
    )
VALUES
    (1, 1, 'Olá, gostaria de saber mais sobre as atividades da ONG.', '2024-06-01 10:00:00'),
    (2, 2, 'Estou interessado em participar do próximo evento.', '2024-06-02 11:30:00'),
    (3, 3, 'Quais são as necessidades atuais da ONG?', '2024-06-03 14:15:00'),
    (4, 4, 'Gostaria de me voluntariar para o evento de julho.', '2024-06-04 09:45:00'),
    (5, 5, 'Como posso ajudar na arrecadação de fundos?', '2024-06-05 16:20:00'),
    (6, 6, 'Estou disponível para ajudar no mutirão de limpeza.', '2024-06-06 08:30:00'),
    (7, 7, 'Quais são os próximos passos para a curadoria?', '2024-06-07 12:00:00'),
    (8, 8, 'Gostaria de receber mais informações sobre as doações.', '2024-06-08 15:10:00'),
    (3, 7, 'Agradeço pela oportunidade de participar.', '2024-06-09 17:45:00'),
    (1, 5, 'Estou ansioso para o evento deste mês.', '2024-06-10 19:30:00');

INSERT INTO
    tb_notificacao (
        fk_voluntario_id,
        notificacao_msg,
        notificacao_data
    )
VALUES
    (1, 'Você foi aprovado na curadoria de projetos.', '2024-06-01 10:00:00'),
    (2, 'Novo evento agendado para o próximo mês.', '2024-06-02 11:30:00'),
    (3, 'Sua doação foi recebida com sucesso.', '2024-06-03 14:15:00'),
    (4, 'Lembrete: reunião de voluntários amanhã.', '2024-06-04 09:45:00'),
    (5, 'Avaliação do seu trabalho foi enviada.', '2024-06-05 16:20:00'),
    (6, 'Novo cargo disponível para voluntários.', '2024-06-06 08:30:00'),
    (7, 'Você recebeu uma nova mensagem da ONG.', '2024-06-07 12:00:00'),
    (8, 'Ação solidária programada para este fim de semana.', '2024-06-08 15:10:00'),
    (1, 'Parabéns! Você completou sua primeira tarefa.', '2024-06-09 17:45:00'),
    (2, 'Novo projeto iniciado na sua área de atuação.', '2024-06-10 19:30:00');

CREATE VIEW
    vw_voluntarios_eventos AS
SELECT
    v.voluntario_nome,
    e.evento_titulo,
    e.evento_data_inicio,
    e.evento_endereco
FROM
    tb_voluntario v
    JOIN tb_voluntario_evento ve ON v.voluntario_id = ve.fk_voluntario_id
    JOIN tb_evento e ON ve.fk_evento_id = e.evento_id;

CREATE VIEW
    vw_doacoes_voluntarios AS
SELECT
    v.voluntario_nome,
    d.doacao_tipo,
    d.doacao_valor,
    d.doacao_data,
    o.ong_nome,
    dest.destinatario_nome
FROM
    tb_doacao d
    JOIN tb_voluntario v ON d.fk_voluntario_id = v.voluntario_id
    JOIN tb_ong o ON d.fk_ong_id = o.ong_id
    JOIN tb_destinatario dest ON d.fk_destinatario_id = dest.destinatario_id;

CREATE VIEW
    vw_avaliacoes_voluntarios AS
SELECT
    v.voluntario_nome,
    a.avaliacao_nota,
    a.avaliacao_comentario
FROM
    tb_avaliacao a
    JOIN tb_voluntario v ON a.fk_voluntario_id = v.voluntario_id;

CREATE VIEW
    vw_voluntarios_cargos AS
SELECT
    v.voluntario_nome,
    c.cargo_nome
FROM
    tb_voluntario v
    JOIN tb_voluntario_cargo vc ON v.voluntario_id = vc.fk_voluntario_id
    JOIN tb_cargos c ON vc.fk_cargo_id = c.cargo_id;

CREATE VIEW
    vw_ongs_eventos AS
SELECT
    o.ong_nome,
    COUNT(e.evento_id) AS total_eventos
FROM
    tb_ong o
    LEFT JOIN tb_evento e ON o.ong_id = e.fk_ong_id
GROUP BY
    o.ong_nome;

CREATE VIEW 
    vw_curadorias_ongs AS
SELECT
    c.curadoria_nome,
    o.ong_nome,
    c.curadoria_requisitos_minimos
FROM
    tb_curadoria c
    JOIN tb_ong o ON c.fk_ong_id = o.ong_id;
    
CREATE VIEW
    vw_mensagens_ongs AS
SELECT
    v.voluntario_nome,
    o.ong_nome,
    m.mensagem_conteudo,
    m.mensagem_data_envio
FROM
    tb_mensagem m
    JOIN tb_voluntario v ON m.fk_voluntario_id = v.voluntario_id
    JOIN tb_ong o ON m.fk_ong_id = o.ong_id;

CREATE VIEW
    vw_notificacoes_voluntarios AS
SELECT
    v.voluntario_nome,
    n.notificacao_msg,
    n.notificacao_data
FROM
    tb_notificacao n
    JOIN tb_voluntario v ON n.fk_voluntario_id = v.voluntario_id;

CREATE VIEW
    vw_voluntarios_ongs_enderecos AS
SELECT
    v.voluntario_nome,
    v.voluntario_endereco,
    o.ong_nome,
    o.ong_endereco
FROM
    tb_voluntario v
    JOIN tb_ong o ON v.voluntario_id = o.ong_id;

CREATE VIEW 
    vw_aprovado_pendentes_voluntarios_curadorias AS
SELECT
    v.voluntario_nome,
    c.curadoria_nome,
    vc.voluntario_curadoria_status_aprovacao
FROM
    tb_voluntario_curadoria vc
    JOIN tb_voluntario v ON vc.fk_voluntario_id = v.voluntario_id
    JOIN tb_curadoria c ON vc.fk_curadoria_id = c.curadoria_id
WHERE
    vc.voluntario_curadoria_status_aprovacao IN ('Aprovado', 'Pendente');
CREATE VIEW 
    vw_voluntarios_cadastrados AS
SELECT
    v.voluntario_id,
    v.voluntario_nome,
    o.ong_id,
    o.ong_nome,
    o.ong_area_atuacao
FROM
    tb_voluntario v
    JOIN tb_ong o ON v.voluntario_id = o.ong_id
WHERE
    v.voluntario_id IS NOT NULL;

CREATE VIEW 
    vw_eventos_cadastrados AS
SELECT
    e.evento_id,
    e.evento_titulo,
    e.evento_data_inicio,
    e.evento_data_termino,
    o.ong_id,
    o.ong_nome
FROM
    tb_evento e
    JOIN tb_ong o ON e.fk_ong_id = o.ong_id
WHERE
    e.evento_id IS NOT NULL;
    
DELIMITER $$

CREATE PROCEDURE sp_inserir_mensagem(
    IN p_voluntario_id INT,
    IN p_ong_id INT,
    IN p_mensagem_conteudo TEXT
)
BEGIN 
    DECLARE mensagem_id INT DEFAULT NULL;

    START TRANSACTION;

    IF p_voluntario_id IS NOT NULL AND p_ong_id IS NOT NULL AND p_mensagem_conteudo IS NOT NULL THEN
        INSERT INTO tb_mensagem (
            fk_voluntario_id,
            fk_ong_id,
            mensagem_conteudo,
            mensagem_data_envio
        )
        VALUES (
            p_voluntario_id,
            p_ong_id,
            p_mensagem_conteudo,
            NOW()
        );
        SET mensagem_id := (SELECT LAST_INSERT_ID());

        INSERT INTO tb_notificacao (
            fk_voluntario_id,
            notificacao_msg,
            notificacao_data
        )
        VALUES (
            p_voluntario_id,
            CONCAT('Nova mensagem enviada: ', p_mensagem_conteudo),
            NOW()
        );
    END IF;

    COMMIT;

    SELECT mensagem_id;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_atualizar_notificacao(
    IN p_notificacao_id INT,
    IN p_notificacao_msg TEXT
)
BEGIN 
    IF p_notificacao_id IS NOT NULL AND p_notificacao_msg IS NOT NULL THEN
        UPDATE tb_notificacao
        SET 
            notificacao_msg = p_notificacao_msg,
            notificacao_data = NOW()
        WHERE
            notificacao_id = p_notificacao_id;
    END IF;

    SELECT p_notificacao_id AS notificacao_id;
END $$

DELIMITER ;

START TRANSACTION;

SET @ong_id = 1;
SET @curadoria_nome = 'Curadoria de Voluntários';
SET @curadoria_requisitos = 'Experiência em gestão de equipes';
SET @voluntario_id = 1;
SET @status_aprovacao = 'Pendente';
SET @curadoria_id = NULL;

INSERT INTO tb_curadoria (
    curadoria_nome,
    curadoria_requisitos_minimos,
    fk_ong_id
)
VALUES (
    @curadoria_nome,
    @curadoria_requisitos,
    @ong_id
);
SET @curadoria_id := (SELECT LAST_INSERT_ID());

INSERT INTO tb_voluntario_curadoria (
    fk_voluntario_id,
    fk_curadoria_id,
    voluntario_curadoria_status_aprovacao
)
VALUES (
    @voluntario_id,
    @curadoria_id,
    @status_aprovacao
);

COMMIT;

