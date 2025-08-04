-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`curadoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`curadoria` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` INT(11) NOT NULL,
  `telefone` BIGINT(11) NOT NULL,
  `sexo` CHAR(1) NOT NULL,
  `profissao` VARCHAR(45) NOT NULL,
  `data_nasc` DATE NOT NULL,
  `idade` SMALLINT(2) NOT NULL,
  `logradouro` VARCHAR(45) NOT NULL,
  `cep` INT(8) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `compl` VARCHAR(45) NULL DEFAULT NULL,
  `bairro` VARCHAR(45) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  `numero` CHAR(4) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`sexo` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`voluntario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`voluntario` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` INT(11) NOT NULL,
  `telefone` BIGINT(11) NOT NULL,
  `sexo` CHAR(1) NOT NULL,
  `area_atuacao` VARCHAR(45) NOT NULL,
  `data_nasc` DATE NOT NULL,
  `idade` SMALLINT(2) NOT NULL,
  `avaliacao` SMALLINT(5) NULL DEFAULT NULL,
  `status` CHAR(3) NOT NULL,
  `logradouro` VARCHAR(45) NOT NULL,
  `cep` INT(8) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `compl` VARCHAR(45) NULL DEFAULT NULL,
  `bairro` VARCHAR(45) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  `numero` CHAR(4) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `curadoria_codigo` SMALLINT(4) NOT NULL,
  `cargos_codigo` SMALLINT(4) NOT NULL,
  PRIMARY KEY (`codigo`, `curadoria_codigo`, `cargos_codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`sexo` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  INDEX `fk_voluntario_curadoria1_idx` (`curadoria_codigo` ASC) VISIBLE,
  INDEX `fk_voluntario_cargos1_idx` (`cargos_codigo` ASC) VISIBLE,
  CONSTRAINT `fk_voluntario_curadoria1`
    FOREIGN KEY (`curadoria_codigo`)
    REFERENCES `mydb`.`curadoria` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_voluntario_cargos1`
    FOREIGN KEY (`cargos_codigo`)
    REFERENCES `mydb`.`cargos` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ong`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ong` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `nome_ong` VARCHAR(45) NOT NULL,
  `cnpj` BIGINT(14) NOT NULL,
  `telefone` BIGINT(11) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `responsavel` VARCHAR(45) NOT NULL,
  `link_site` VARCHAR(45) NULL DEFAULT NULL,
  `area_atuacao` VARCHAR(45) NOT NULL,
  `avaliacao` SMALLINT(5) NULL DEFAULT NULL,
  `status` CHAR(3) NOT NULL,
  `logradouro` VARCHAR(45) NOT NULL,
  `cep` INT(8) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `compl` VARCHAR(45) NULL DEFAULT NULL,
  `bairro` VARCHAR(45) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  `numero` CHAR(4) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `curadoria_codigo` SMALLINT(4) NOT NULL,
  `voluntario_codigo` SMALLINT(4) NOT NULL,
  PRIMARY KEY (`codigo`, `curadoria_codigo`, `voluntario_codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_ong_curadoria1_idx` (`curadoria_codigo` ASC) VISIBLE,
  INDEX `fk_ong_voluntario1_idx` (`voluntario_codigo` ASC) VISIBLE,
  CONSTRAINT `fk_ong_curadoria1`
    FOREIGN KEY (`curadoria_codigo`)
    REFERENCES `mydb`.`curadoria` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ong_voluntario1`
    FOREIGN KEY (`voluntario_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`cargos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`cargos` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_termino` DATE NOT NULL,
  `status` CHAR(3) NOT NULL,
  `modalidade` VARCHAR(45) NOT NULL,
  `descricao` VARCHAR(200) NOT NULL,
  `req_minimos` VARCHAR(45) NOT NULL,
  `ong_codigo` SMALLINT(4) NOT NULL,
  `curadoria_codigo` SMALLINT(4) NOT NULL,
  `voluntario_codigo` SMALLINT(4) NOT NULL,
  PRIMARY KEY (`codigo`, `ong_codigo`, `curadoria_codigo`, `voluntario_codigo`),
  INDEX `fk_cargos_ong_idx` (`ong_codigo` ASC) VISIBLE,
  INDEX `fk_cargos_curadoria1_idx` (`curadoria_codigo` ASC) VISIBLE,
  INDEX `fk_cargos_voluntario1_idx` (`voluntario_codigo` ASC) VISIBLE,
  CONSTRAINT `fk_cargos_ong`
    FOREIGN KEY (`ong_codigo`)
    REFERENCES `mydb`.`ong` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cargos_curadoria1`
    FOREIGN KEY (`curadoria_codigo`)
    REFERENCES `mydb`.`curadoria` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cargos_voluntario1`
    FOREIGN KEY (`voluntario_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`evento` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `data` DATE NOT NULL,
  `local` VARCHAR(100) NOT NULL,
  `descricao` TEXT NULL DEFAULT NULL,
  `ong_codigo` SMALLINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  INDEX (`ong_codigo` ASC) VISIBLE,
  CONSTRAINT ``
    FOREIGN KEY (`ong_codigo`)
    REFERENCES `mydb`.`ong` (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`doacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`doacao` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `data` DATE NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `voluntario_codigo` SMALLINT(4) NULL DEFAULT NULL,
  `ong_codigo` SMALLINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  INDEX (`voluntario_codigo` ASC) VISIBLE,
  INDEX (`ong_codigo` ASC) VISIBLE,
  CONSTRAINT ``
    FOREIGN KEY (`voluntario_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`),
  CONSTRAINT ``
    FOREIGN KEY (`ong_codigo`)
    REFERENCES `mydb`.`ong` (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`avaliacao` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `nota` SMALLINT(5) NOT NULL,
  `comentario` TEXT NULL DEFAULT NULL,
  `data` DATE NOT NULL,
  `curadoria_codigo` SMALLINT(4) NULL DEFAULT NULL,
  `voluntario_codigo` SMALLINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  INDEX (`curadoria_codigo` ASC) VISIBLE,
  INDEX (`voluntario_codigo` ASC) VISIBLE,
  CONSTRAINT ``
    FOREIGN KEY (`curadoria_codigo`)
    REFERENCES `mydb`.`curadoria` (`codigo`),
  CONSTRAINT ``
    FOREIGN KEY (`voluntario_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`mensagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`mensagem` (
  `codigo` SMALLINT(4) NOT NULL AUTO_INCREMENT,
  `texto` TEXT NOT NULL,
  `data_envio` DATETIME NOT NULL,
  `remetente_codigo` SMALLINT(4) NULL DEFAULT NULL,
  `destinatario_codigo` SMALLINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  INDEX (`remetente_codigo` ASC) VISIBLE,
  INDEX (`destinatario_codigo` ASC) VISIBLE,
  CONSTRAINT ``
    FOREIGN KEY (`remetente_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`),
  CONSTRAINT ``
    FOREIGN KEY (`destinatario_codigo`)
    REFERENCES `mydb`.`voluntario` (`codigo`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
