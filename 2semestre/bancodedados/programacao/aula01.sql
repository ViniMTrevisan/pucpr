create database if not exists db_escola;
use db_escola;
create table tb_usuario(
	tb_usuario_id int primary key auto_increment,
    tb_nome varchar(50) not null,
    tb_sexo varchar(10) not null,
    tb_serie varchar(50) not null,
);
create table tb_professor(
	tb_professor_id int primary key auto_increment,
    tb_materia varchar (50) not null,
    tb_nome varchar (50) not null,
);