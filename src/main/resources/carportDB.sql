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
CREATE SCHEMA IF NOT EXISTS `carport` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `carport` ;

-- -----------------------------------------------------
-- Table `carport`.`postnr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`postnr` (
  `idpostnr` INT NOT NULL AUTO_INCREMENT,
  `postnr` INT NOT NULL,
  PRIMARY KEY (`idpostnr`),
  UNIQUE INDEX `idpostnr_UNIQUE` (`idpostnr` ASC) VISIBLE,
  UNIQUE INDEX `postnr_UNIQUE` (`postnr` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`bruger`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`bruger` (
  `idbruger` INT NOT NULL AUTO_INCREMENT,
  `mail` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `adresse` VARCHAR(45) NOT NULL,
  `postnr` INT NOT NULL,
  PRIMARY KEY (`idbruger`),
  UNIQUE INDEX `idbruger_UNIQUE` (`idbruger` ASC) VISIBLE,
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE,
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC) VISIBLE,
  INDEX `fk_bruger_postnr1_idx` (`postnr` ASC) VISIBLE,
  CONSTRAINT `fk_bruger_postnr1`
    FOREIGN KEY (`postnr`)
    REFERENCES `carport`.`postnr` (`idpostnr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`bestilling`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`bestilling` (
  `idbestilling` INT NOT NULL AUTO_INCREMENT,
  `dato` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bruger` INT NOT NULL,
  PRIMARY KEY (`idbestilling`),
  UNIQUE INDEX `idbestilling_UNIQUE` (`idbestilling` ASC) VISIBLE,
  INDEX `fk_bestilling_bruger1_idx` (`bruger` ASC) VISIBLE,
  CONSTRAINT `fk_bestilling_bruger1`
    FOREIGN KEY (`bruger`)
    REFERENCES `carport`.`bruger` (`idbruger`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`stykliste`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`stykliste` (
  `idstykliste` INT NOT NULL AUTO_INCREMENT,
  `bruger` VARCHAR(45) NOT NULL,
  `samlet_pris` INT NOT NULL,
  `bestillingsId` INT NOT NULL,
  PRIMARY KEY (`idstykliste`),
  UNIQUE INDEX `idstykliste_UNIQUE` (`idstykliste` ASC) VISIBLE,
  INDEX `fk_stykliste_bestilling1_idx` (`bestillingsId` ASC) VISIBLE,
  CONSTRAINT `fk_stykliste_bestilling1`
    FOREIGN KEY (`bestillingsId`)
    REFERENCES `carport`.`bestilling` (`idbestilling`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`rem_længde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`rem_længde` (
  `idrem_længde` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `pris` INT NOT NULL,
  PRIMARY KEY (`idrem_længde`),
  UNIQUE INDEX `idrem_længde_UNIQUE` (`idrem_længde` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `pris_UNIQUE` (`pris` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`rem_størrelse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`rem_størrelse` (
  `idrem_størrelse` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `brede` INT NOT NULL,
  `højde` INT NOT NULL,
  PRIMARY KEY (`idrem_størrelse`),
  UNIQUE INDEX `idrem_størrelse_UNIQUE` (`idrem_størrelse` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `brede_UNIQUE` (`brede` ASC) VISIBLE,
  UNIQUE INDEX `højde_UNIQUE` (`højde` ASC) VISIBLE,
  CONSTRAINT `fk_rem_størrelse_rem_længde1`
    FOREIGN KEY (`længde`)
    REFERENCES `carport`.`rem_længde` (`idrem_længde`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`rem_produkt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`rem_produkt` (
  `idremme` INT NOT NULL AUTO_INCREMENT,
  `størrelse` INT NOT NULL,
  `pris` INT NOT NULL,
  `antal` INT NOT NULL,
  `styklisteId` INT NOT NULL,
  PRIMARY KEY (`idremme`),
  UNIQUE INDEX `idremme_UNIQUE` (`idremme` ASC) VISIBLE,
  INDEX `fk_rem_rem_størrelse1_idx` (`størrelse` ASC) VISIBLE,
  INDEX `fk_rem_produkt_stykliste1_idx` (`styklisteId` ASC) VISIBLE,
  CONSTRAINT `fk_rem_rem_størrelse1`
    FOREIGN KEY (`størrelse`)
    REFERENCES `carport`.`rem_størrelse` (`idrem_størrelse`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rem_produkt_stykliste1`
    FOREIGN KEY (`styklisteId`)
    REFERENCES `carport`.`stykliste` (`idstykliste`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`spær_længde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`spær_længde` (
  `idspær_længde` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `pris` INT NOT NULL,
  PRIMARY KEY (`idspær_længde`),
  UNIQUE INDEX `idspær_længde_UNIQUE` (`idspær_længde` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `pris_UNIQUE` (`pris` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`spær_størrelse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`spær_størrelse` (
  `idspær_størrelse` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `brede` INT NOT NULL,
  `højde` INT NOT NULL,
  PRIMARY KEY (`idspær_størrelse`),
  UNIQUE INDEX `idspær_størrelse_UNIQUE` (`idspær_størrelse` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `brede_UNIQUE` (`brede` ASC) VISIBLE,
  UNIQUE INDEX `højde_UNIQUE` (`højde` ASC) VISIBLE,
  CONSTRAINT `fk_spær_størrelse_spær_længde1`
    FOREIGN KEY (`længde`)
    REFERENCES `carport`.`spær_længde` (`idspær_længde`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`spær_produkt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`spær_produkt` (
  `idspær` INT NOT NULL AUTO_INCREMENT,
  `størrelse` INT NOT NULL,
  `pris` INT NOT NULL,
  `antal` INT NOT NULL,
  `styklisteId` INT NOT NULL,
  PRIMARY KEY (`idspær`),
  UNIQUE INDEX `idspær_UNIQUE` (`idspær` ASC) VISIBLE,
  INDEX `fk_spær_spær_størrelse1_idx` (`størrelse` ASC) VISIBLE,
  INDEX `fk_spær_produkt_stykliste1_idx` (`styklisteId` ASC) VISIBLE,
  CONSTRAINT `fk_spær_spær_størrelse1`
    FOREIGN KEY (`størrelse`)
    REFERENCES `carport`.`spær_størrelse` (`idspær_størrelse`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spær_produkt_stykliste1`
    FOREIGN KEY (`styklisteId`)
    REFERENCES `carport`.`stykliste` (`idstykliste`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`stolpe_længde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`stolpe_længde` (
  `idstolpe_længde` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `pris` INT NOT NULL,
  PRIMARY KEY (`idstolpe_længde`),
  UNIQUE INDEX `idstolpe_længde_UNIQUE` (`idstolpe_længde` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `pris_UNIQUE` (`pris` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`stolpe_størrelse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`stolpe_størrelse` (
  `idstolpe_størrelse` INT NOT NULL AUTO_INCREMENT,
  `længde` INT NOT NULL,
  `brede` INT NOT NULL,
  `højde` INT NOT NULL,
  PRIMARY KEY (`idstolpe_størrelse`),
  UNIQUE INDEX `idstolpe_størrelse_UNIQUE` (`idstolpe_størrelse` ASC) VISIBLE,
  UNIQUE INDEX `længde_UNIQUE` (`længde` ASC) VISIBLE,
  UNIQUE INDEX `brede_UNIQUE` (`brede` ASC) VISIBLE,
  UNIQUE INDEX `højde_UNIQUE` (`højde` ASC) VISIBLE,
  CONSTRAINT `fk_stolpe_størrelse_stolpe_længde1`
    FOREIGN KEY (`længde`)
    REFERENCES `carport`.`stolpe_længde` (`idstolpe_længde`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`stolpe_produkt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`stolpe_produkt` (
  `idstolper` INT NOT NULL AUTO_INCREMENT,
  `størrelse` INT NOT NULL,
  `pris` INT NOT NULL,
  `antal` INT NOT NULL,
  `styklisteId` INT NOT NULL,
  PRIMARY KEY (`idstolper`),
  UNIQUE INDEX `idstolper_UNIQUE` (`idstolper` ASC) VISIBLE,
  INDEX `fk_stolpe_stolpe_størrelse_idx` (`størrelse` ASC) VISIBLE,
  INDEX `fk_stolpe_produkt_stykliste1_idx` (`styklisteId` ASC) VISIBLE,
  CONSTRAINT `fk_stolpe_stolpe_størrelse`
    FOREIGN KEY (`størrelse`)
    REFERENCES `carport`.`stolpe_størrelse` (`idstolpe_størrelse`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stolpe_produkt_stykliste1`
    FOREIGN KEY (`styklisteId`)
    REFERENCES `carport`.`stykliste` (`idstykliste`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `carport`.`ordre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`ordre` (
  `idordre` INT NOT NULL AUTO_INCREMENT,
  `dato` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bruger` INT NOT NULL,
  `pris` INT NOT NULL,
  `bestillingsId` INT NOT NULL,
  PRIMARY KEY (`idordre`),
  UNIQUE INDEX `idordre_UNIQUE` (`idordre` ASC) VISIBLE,
  INDEX `fk_ordre_bestilling1_idx` (`bestillingsId` ASC) VISIBLE,
  INDEX `fk_ordre_bruger1_idx` (`bruger` ASC) VISIBLE,
  CONSTRAINT `fk_ordre_bestilling1`
    FOREIGN KEY (`bestillingsId`)
    REFERENCES `carport`.`bestilling` (`idbestilling`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordre_bruger1`
    FOREIGN KEY (`bruger`)
    REFERENCES `carport`.`bruger` (`idbruger`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
