CREATE DATABASE ImpactPro;
USE ImpactPro;

CREATE TABLE curadoria (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    cpf BIGINT,
    telefone BIGINT NOT NULL,
    sexo CHAR(1) NOT NULL,
    profissao VARCHAR(45) NOT NULL,
    data_nasc DATE NOT NULL,
    idade INT NOT NULL,
    logradouro VARCHAR(45) NOT NULL,
    cep INT NOT NULL,
    uf CHAR(2) NOT NULL,
    complemento VARCHAR(45),
    bairro VARCHAR(45) NOT NULL,
    cidade VARCHAR(45) NOT NULL,
    numero CHAR(4) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE voluntario (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    cpf BIGINT NOT NULL UNIQUE,
    telefone BIGINT NOT NULL,
    sexo CHAR(1) NOT NULL,
    area_atuacao VARCHAR(45) NOT NULL,
    data_nasc DATE NOT NULL,
    idade INT NOT NULL,
    avaliacao INT,
    status CHAR(3) NOT NULL,
    logradouro VARCHAR(45) NOT NULL,
    cep INT NOT NULL,
    uf CHAR(2) NOT NULL,
    complemento VARCHAR(45),
    bairro VARCHAR(45) NOT NULL,
    cidade VARCHAR(45) NOT NULL,
    numero CHAR(4) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    curadoria_codigo INT,
    cargo_codigo INT,
    FOREIGN KEY (curadoria_codigo) REFERENCES curadoria(codigo),
    FOREIGN KEY (cargo_codigo) REFERENCES cargo(codigo)
);

CREATE TABLE ong (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome_ong VARCHAR(45) NOT NULL,
    cnpj BIGINT NOT NULL UNIQUE,
    telefone BIGINT NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    responsavel VARCHAR(45) NOT NULL,
    link_site VARCHAR(45),
    area_atuacao VARCHAR(45) NOT NULL,
    avaliacao INT,
    status CHAR(3) NOT NULL,
    logradouro VARCHAR(45) NOT NULL,
    cep INT NOT NULL,
    uf CHAR(2) NOT NULL,
    complemento VARCHAR(45),
    bairro VARCHAR(45) NOT NULL,
    cidade VARCHAR(45) NOT NULL,
    numero CHAR(4) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    curadoria_codigo INT,
    voluntario_codigo INT,
    FOREIGN KEY (curadoria_codigo) REFERENCES curadoria(codigo),
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo)
);

CREATE TABLE cargo (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(45) NOT NULL,
    data_inicio DATE NOT NULL,
    data_termino DATE NOT NULL,
    status CHAR(3) NOT NULL,
    modalidade VARCHAR(45) NOT NULL,
    descricao VARCHAR(200),
    requisitos_minimos VARCHAR(45),
    ong_codigo INT,
    curadoria_codigo INT,
    voluntario_codigo INT,
    FOREIGN KEY (curadoria_codigo) REFERENCES curadoria(codigo),
    FOREIGN KEY (ong_codigo) REFERENCES ong(codigo),
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo)
);

CREATE TABLE evento (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data DATE NOT NULL,
    local VARCHAR(100) NOT NULL,
    descricao VARCHAR(200),
    tipo VARCHAR(45),
    ong_codigo INT,
    FOREIGN KEY (ong_codigo) REFERENCES ong(codigo)
);

CREATE TABLE voluntario_evento (
    voluntario_codigo INT,
    evento_codigo INT,
    PRIMARY KEY (voluntario_codigo, evento_codigo),
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo),
    FOREIGN KEY (evento_codigo) REFERENCES evento(codigo)
);

CREATE TABLE doacao (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(45) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data DATE NOT NULL,
    voluntario_codigo INT,
    ong_codigo INT,
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo),
    FOREIGN KEY (ong_codigo) REFERENCES ong(codigo)
);

CREATE TABLE avaliacao (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nota INT NOT NULL,
    comentario VARCHAR(200),
    data DATE NOT NULL,
    voluntario_codigo INT,
    ong_codigo INT,
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo),
    FOREIGN KEY (ong_codigo) REFERENCES ong(codigo)
);

CREATE TABLE mensagem (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    texto VARCHAR(255) NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(45),
    voluntario_codigo INT,
    curadoria_codigo INT,
    FOREIGN KEY (voluntario_codigo) REFERENCES voluntario(codigo),
    FOREIGN KEY (curadoria_codigo) REFERENCES curadoria(codigo)
);
