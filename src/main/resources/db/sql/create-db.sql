-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  user_id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(25) NOT NULL ,
  password VARCHAR(25) NOT NULL ,
  full_name VARCHAR(45) NOT NULL ,
  birthday DATE NOT NULL ,
  user_role VARCHAR(25) NULL ,
  PRIMARY KEY (user_id)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table discount_statistic
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS discount_statistic (
  discount_type VARCHAR(20) NOT NULL ,
  applied_count INT NOT NULL ,
  user_id INT NOT NULL ,
  PRIMARY KEY (discount_type, user_id)  ,
  CONSTRAINT user_id_fk
    FOREIGN KEY (user_id)
    REFERENCES user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table event
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS event (
  event_id INT NOT NULL AUTO_INCREMENT,
  event_name VARCHAR(25) NULL ,
  rating VARCHAR(20) NULL ,
  base_price DECIMAL(5,2) NULL ,
  PRIMARY KEY (event_id)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table event_statistic
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS event_statistic (
  event_statistic_id INT NOT NULL AUTO_INCREMENT,
  event_name VARCHAR(20) NOT NULL ,
  access_count INT NULL ,
  price_query_count INT NULL ,
  booked_count INT NULL ,
  PRIMARY KEY (event_name)  ,
  CONSTRAINT event_id_fk
    FOREIGN KEY (event_name)
    REFERENCES event (event_name)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table schedule
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS schedule (
  schedule_id INT NOT NULL AUTO_INCREMENT ,
  event_name VARCHAR(20) NULL ,
  auditorium VARCHAR(20) NULL ,
  event_date DATE NULL ,
  PRIMARY KEY (schedule_id)  ,
  CONSTRAINT schedule_event_id_fk
    FOREIGN KEY (event_name)
    REFERENCES event (event_name)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

