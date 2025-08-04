CREATE DATABASE musica_db;
USE musica_db;

CREATE TABLE musica (
  id_musica INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100),
  duracao FLOAT,
  compositor VARCHAR(100),
  album VARCHAR(100)
);
