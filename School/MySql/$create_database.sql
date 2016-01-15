-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema szkola004
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `szkola004` ;

-- -----------------------------------------------------
-- Schema szkola004
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `szkola004` DEFAULT CHARACTER SET utf8 ;
USE `szkola004` ;

-- -----------------------------------------------------
-- Table `szkola004`.`lektor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`lektor` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`lektor` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(25) NULL,
  `miasto` VARCHAR(25) NULL,
  `telefon` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `umowa` VARCHAR(15) NULL,
  `nip` VARCHAR(15) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`firma`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`firma` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`firma` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nip` VARCHAR(15) NULL,
  `nazwa` VARCHAR(25) NULL,
  `symbol` VARCHAR(10) NULL,
  `miasto` VARCHAR(25) NULL,
  `osoba` VARCHAR(45) NULL,
  `telefon` VARCHAR(15) NULL,
  `komorka` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `adres` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`kursant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`kursant` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`kursant` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(25) NULL,
  `telefon` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `firma_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_kursant_firma1_idx` (`firma_id` ASC),
  CONSTRAINT `fk_kursant_firma1`
    FOREIGN KEY (`firma_id`)
    REFERENCES `szkola004`.`firma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`jezyk`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`jezyk` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`jezyk` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(15) NULL,
  `symbol` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`program`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`program` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`program` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `referencja` VARCHAR(45) NULL,
  `metoda` VARCHAR(25) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`kurs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`kurs` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`kurs` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `symbol` VARCHAR(10) NULL,
  `opis` VARCHAR(45) NULL,
  `rok` VARCHAR(10) NULL,
  `semestr` VARCHAR(10) NULL,
  `sala` VARCHAR(45) NULL,
  `firma_id` INT UNSIGNED NULL,
  `lektor_id` INT UNSIGNED NULL,
  `jezyk_id` INT UNSIGNED NULL,
  `program_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_kurs_firma1_idx` (`firma_id` ASC),
  INDEX `fk_kurs_lektor1_idx` (`lektor_id` ASC),
  INDEX `fk_kurs_jezyk1_idx` (`jezyk_id` ASC),
  INDEX `fk_kurs_program1_idx` (`program_id` ASC),
  CONSTRAINT `fk_kurs_firma1`
    FOREIGN KEY (`firma_id`)
    REFERENCES `szkola004`.`firma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kurs_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kurs_jezyk1`
    FOREIGN KEY (`jezyk_id`)
    REFERENCES `szkola004`.`jezyk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kurs_program1`
    FOREIGN KEY (`program_id`)
    REFERENCES `szkola004`.`program` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`podrecznik`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`podrecznik` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`podrecznik` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NULL,
  `poziom` VARCHAR(5) NULL,
  `jezyk_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_podrecznik_jezyk1_idx` (`jezyk_id` ASC),
  CONSTRAINT `fk_podrecznik_jezyk1`
    FOREIGN KEY (`jezyk_id`)
    REFERENCES `szkola004`.`jezyk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`test` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`test` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rodzaj` VARCHAR(15) NULL,
  `ocena` INT NULL,
  `kurs_id` INT UNSIGNED NULL,
  `kursant_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_kurs1_idx` (`kurs_id` ASC),
  INDEX `fk_test_kursant1_idx` (`kursant_id` ASC),
  CONSTRAINT `fk_test_kurs1`
    FOREIGN KEY (`kurs_id`)
    REFERENCES `szkola004`.`kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test_kursant1`
    FOREIGN KEY (`kursant_id`)
    REFERENCES `szkola004`.`kursant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`wplata`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`wplata` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`wplata` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `kwota` DECIMAL(15,2) NULL,
  `opis` VARCHAR(45) NULL,
  `firma_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wplata_firma1_idx` (`firma_id` ASC),
  CONSTRAINT `fk_wplata_firma1`
    FOREIGN KEY (`firma_id`)
    REFERENCES `szkola004`.`firma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`wyplata`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`wyplata` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`wyplata` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `kwota` DECIMAL(15,2) NULL,
  `opis` VARCHAR(45) NULL,
  `lektor_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wyplata_lektor1_idx` (`lektor_id` ASC),
  CONSTRAINT `fk_wyplata_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`wypozyczenie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`wypozyczenie` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`wypozyczenie` (
  `podrecznik_id` INT UNSIGNED NOT NULL,
  `lektor_id` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  INDEX `fk_wypozyczenia_podreczniki1_idx` (`podrecznik_id` ASC),
  INDEX `fk_wypozyczenia_lektorzy1_idx` (`lektor_id` ASC),
  PRIMARY KEY (`lektor_id`, `podrecznik_id`),
  CONSTRAINT `fk_wypozyczenia_podreczniki1`
    FOREIGN KEY (`podrecznik_id`)
    REFERENCES `szkola004`.`podrecznik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wypozyczenia_lektorzy1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`ankieta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`ankieta` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`ankieta` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `pytanie_1` TINYINT(1) NULL,
  `pytanie_2` TINYINT(1) NULL,
  `pytanie_3` TINYINT(1) NULL,
  `pytanie_4` TINYINT(1) NULL,
  `pytanie_5` TINYINT(1) NULL,
  `pytanie_6` TINYINT(1) NULL,
  `pytanie_7` TINYINT(1) NULL,
  `pytanie_8` TINYINT(1) NULL,
  `pytanie_9` TINYINT(1) NULL,
  `pytanie_10` TINYINT(1) NULL,
  `pytanie_11` TINYINT(1) NULL,
  `pytanie_12` TINYINT(1) NULL,
  `pytanie_13` TINYINT(1) NULL,
  `pytanie_14` TINYINT(1) NULL,
  `pytanie_15` TINYINT(1) NULL,
  `pytanie_16` TINYINT(1) NULL,
  `pytanie_17` TINYINT(1) NULL,
  `pytanie_18` TINYINT(1) NULL,
  `pytanie_19` TINYINT(1) NULL,
  `pytanie_20` TINYINT(1) NULL,
  `pytanie_21` TINYINT(1) NULL,
  `pytanie_22` TINYINT(1) NULL,
  `pytanie_opisowe_23` VARCHAR(205) NULL,
  `lektor_id` INT UNSIGNED NULL,
  `kursant_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ankieta_lektor1_idx` (`lektor_id` ASC),
  INDEX `fk_ankieta_kursant1_idx` (`kursant_id` ASC),
  CONSTRAINT `fk_ankieta_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ankieta_kursant1`
    FOREIGN KEY (`kursant_id`)
    REFERENCES `szkola004`.`kursant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`stawka_firmy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`stawka_firmy` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`stawka_firmy` (
  `firma_id` INT UNSIGNED NOT NULL,
  `stawka` DECIMAL(15,2) NULL,
  `natywny` TINYINT(1) NOT NULL,
  INDEX `fk_stawka_firmy_firma1_idx` (`firma_id` ASC),
  PRIMARY KEY (`firma_id`, `natywny`),
  CONSTRAINT `fk_stawka_firmy_firma1`
    FOREIGN KEY (`firma_id`)
    REFERENCES `szkola004`.`firma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '			';


-- -----------------------------------------------------
-- Table `szkola004`.`lekcja`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`lekcja` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`lekcja` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `odwolana` TINYINT(1) NULL,
  `kurs_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_lekcja_kurs1_idx` (`kurs_id` ASC),
  CONSTRAINT `fk_lekcja_kurs1`
    FOREIGN KEY (`kurs_id`)
    REFERENCES `szkola004`.`kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`stawka_lektora`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`stawka_lektora` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`stawka_lektora` (
  `kurs_id` INT UNSIGNED NOT NULL,
  `lektor_id` INT UNSIGNED NOT NULL,
  `stawka` DECIMAL(15,2) NULL,
  INDEX `fk_stawka_lektora_lektor1_idx` (`lektor_id` ASC),
  INDEX `fk_stawka_lektora_kurs1_idx` (`kurs_id` ASC),
  PRIMARY KEY (`kurs_id`, `lektor_id`),
  CONSTRAINT `fk_stawka_lektora_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stawka_lektora_kurs1`
    FOREIGN KEY (`kurs_id`)
    REFERENCES `szkola004`.`kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`jezyk_lektora`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`jezyk_lektora` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`jezyk_lektora` (
  `lektor_id` INT UNSIGNED NOT NULL,
  `jezyk_id` INT UNSIGNED NOT NULL,
  `natywny` TINYINT(1) NULL,
  INDEX `fk_jezyk_lektora_lektor1_idx` (`lektor_id` ASC),
  INDEX `fk_jezyk_lektora_jezyk1_idx` (`jezyk_id` ASC),
  PRIMARY KEY (`lektor_id`, `jezyk_id`),
  CONSTRAINT `fk_jezyk_lektora_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jezyk_lektora_jezyk1`
    FOREIGN KEY (`jezyk_id`)
    REFERENCES `szkola004`.`jezyk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`kurs_kursanta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`kurs_kursanta` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`kurs_kursanta` (
  `kurs_id` INT UNSIGNED NOT NULL,
  `kursant_id` INT UNSIGNED NOT NULL,
  `opis` VARCHAR(45) NULL,
  PRIMARY KEY (`kurs_id`, `kursant_id`),
  INDEX `fk_kurs_kursanta_kursant1_idx` (`kursant_id` ASC),
  CONSTRAINT `fk_kurs_kursanta_kurs1`
    FOREIGN KEY (`kurs_id`)
    REFERENCES `szkola004`.`kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kurs_kursanta_kursant1`
    FOREIGN KEY (`kursant_id`)
    REFERENCES `szkola004`.`kursant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`obecnosc`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`obecnosc` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`obecnosc` (
  `lekcja_id` INT UNSIGNED NOT NULL,
  `kursant_id` INT UNSIGNED NOT NULL,
  `obecny` TINYINT(1) NULL,
  INDEX `fk_obecnosc_lekcja1_idx` (`lekcja_id` ASC),
  INDEX `fk_obecnosc_kursant1_idx` (`kursant_id` ASC),
  PRIMARY KEY (`lekcja_id`, `kursant_id`),
  CONSTRAINT `fk_obecnosc_lekcja1`
    FOREIGN KEY (`lekcja_id`)
    REFERENCES `szkola004`.`lekcja` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_obecnosc_kursant1`
    FOREIGN KEY (`kursant_id`)
    REFERENCES `szkola004`.`kursant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`jezyk_kursanta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`jezyk_kursanta` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`jezyk_kursanta` (
  `kursant_id` INT UNSIGNED NOT NULL,
  `jezyk_id` INT UNSIGNED NOT NULL,
  `poziom` VARCHAR(5) NULL,
  INDEX `fk_jezyk_kursanta_kursant1_idx` (`kursant_id` ASC),
  INDEX `fk_jezyk_kursanta_jezyk1_idx` (`jezyk_id` ASC),
  PRIMARY KEY (`kursant_id`, `jezyk_id`),
  CONSTRAINT `fk_jezyk_kursanta_kursant1`
    FOREIGN KEY (`kursant_id`)
    REFERENCES `szkola004`.`kursant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jezyk_kursanta_jezyk1`
    FOREIGN KEY (`jezyk_id`)
    REFERENCES `szkola004`.`jezyk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`termin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`termin` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`termin` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `dzien` VARCHAR(15) NULL,
  `godzina_start` TIME NULL,
  `godzina_stop` TIME NULL,
  `kurs_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_termin_kurs1_idx` (`kurs_id` ASC),
  CONSTRAINT `fk_termin_kurs1`
    FOREIGN KEY (`kurs_id`)
    REFERENCES `szkola004`.`kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`rachunek`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`rachunek` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`rachunek` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `numer` VARCHAR(25) NULL,
  `data` DATE NULL,
  `kwota` DECIMAL(15,2) NULL,
  `opis` VARCHAR(45) NULL,
  `lektor_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rachunek_lektor1_idx` (`lektor_id` ASC),
  CONSTRAINT `fk_rachunek_lektor1`
    FOREIGN KEY (`lektor_id`)
    REFERENCES `szkola004`.`lektor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `szkola004`.`faktura`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `szkola004`.`faktura` ;

CREATE TABLE IF NOT EXISTS `szkola004`.`faktura` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `numer` VARCHAR(25) NULL,
  `data` DATE NULL,
  `kwota` DECIMAL(15,2) NULL,
  `opis` VARCHAR(45) NULL,
  `firma_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_faktura_firma1_idx` (`firma_id` ASC),
  CONSTRAINT `fk_faktura_firma1`
    FOREIGN KEY (`firma_id`)
    REFERENCES `szkola004`.`firma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
