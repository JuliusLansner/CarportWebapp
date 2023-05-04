-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema carport
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema carport
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS carport_test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE carport_test ;

-- -----------------------------------------------------
-- Table `carport`.`materiale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`materiale` (
  `idmateriale` INT NOT NULL,
  `beskrivelse` VARCHAR(45) NOT NULL,
  `enhed` VARCHAR(45) NOT NULL,
  `pris_per_enhed` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idmateriale`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`postnr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`postnr` (
  `idpostnr` INT NOT NULL AUTO_INCREMENT,
  `postnr` INT NOT NULL,
  PRIMARY KEY (`idpostnr`),
  UNIQUE INDEX `postnr_UNIQUE` (`postnr` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`bruger`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`bruger` (
  `idbruger` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `adresse` VARCHAR(45) NOT NULL,
  `postnr_idpostnr` INT NOT NULL,
  `telefon` INT NOT NULL,
  PRIMARY KEY (`idbruger`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE,
  INDEX `fk_bruger_postnr1_idx` (`postnr_idpostnr` ASC) VISIBLE,
  UNIQUE INDEX `telefon_UNIQUE` (`telefon` ASC) VISIBLE,
  CONSTRAINT `fk_bruger_postnr1`
    FOREIGN KEY (`postnr_idpostnr`)
    REFERENCES carport_test.`postnr` (`idpostnr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`ordre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`ordre` (
  `idordre` INT NOT NULL AUTO_INCREMENT,
  `længde` VARCHAR(45) NOT NULL,
  `brede` VARCHAR(45) NOT NULL,
  `samlet_pris` INT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  `dato` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bruger_id` int NOT NULL,
  PRIMARY KEY (`idordre`),
  UNIQUE INDEX `idordre_UNIQUE` (`idordre` ASC) VISIBLE,
  INDEX `fk_ordre_bruger1_idx` (`bruger_id` ASC) VISIBLE,
  CONSTRAINT `fk_ordre_bruger1`
    FOREIGN KEY (`bruger_id`)
    REFERENCES carport_test.`bruger` (`idbruger`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`stykliste`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`stykliste` (
  `idstykliste` INT NOT NULL AUTO_INCREMENT,
  `pris` INT NOT NULL,
  `bestilling_idbestilling` INT NOT NULL,
  `ordre_idordre` INT NOT NULL,
  PRIMARY KEY (`idstykliste`),
  UNIQUE INDEX `idstykliste_UNIQUE` (`idstykliste` ASC) VISIBLE,
  INDEX `fk_stykliste_ordre1_idx` (`ordre_idordre` ASC) VISIBLE,
  CONSTRAINT `fk_stykliste_ordre1`
    FOREIGN KEY (`ordre_idordre`)
    REFERENCES carport_test.`ordre` (`idordre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`m_variant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carport_test.`m_variant` (
  `idm_variant` INT NOT NULL AUTO_INCREMENT,
  `materiale_id` INT NOT NULL,
  `længde` INT NOT NULL,
  `stykliste_idstykliste` INT NOT NULL,
  PRIMARY KEY (`idm_variant`),
  UNIQUE INDEX `idm_variant_UNIQUE` (`idm_variant` ASC) VISIBLE,
  INDEX `fk_m_variant_materiale1_idx` (`materiale_id` ASC) VISIBLE,
  INDEX `fk_m_variant_stykliste1_idx` (`stykliste_idstykliste` ASC) VISIBLE,
  CONSTRAINT `fk_m_variant_materiale1`
    FOREIGN KEY (`materiale_id`)
    REFERENCES carport_test.`materiale` (`idmateriale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_variant_stykliste1`
    FOREIGN KEY (`stykliste_idstykliste`)
    REFERENCES carport_test.`stykliste` (`idstykliste`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
