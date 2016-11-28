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
  event_id INT NOT NULL ,
  auditorium VARCHAR(20) NULL ,
  event_date DATE NULL ,
  PRIMARY KEY (schedule_id)  ,
  CONSTRAINT schedule_event_id_fk
    FOREIGN KEY (event_id)
    REFERENCES event (event_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table ticket
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ticket (
  ticket_id INT NOT NULL AUTO_INCREMENT ,
  purchased TINYINT(1) NOT NULL ,
  booked TINYINT(1) NOT NULL ,
  discounted TINYINT(1) NOT NULL ,
  event_id INT  NOT NULL ,
  event_date DATE NULL ,
  ticket_price DECIMAL(5,2) NULL ,
  user_id INT NULL,
  PRIMARY KEY (ticket_id)  ,
  CONSTRAINT ticket_event_id_fk
    FOREIGN KEY (event_id)
    REFERENCES event (event_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  user_id INT NOT NULL AUTO_INCREMENT ,
  email VARCHAR(20) NOT NULL ,
  password VARCHAR(20)  NULL ,
  full_name VARCHAR(20)  NULL ,
  birthday DATE NULL ,
  user_role VARCHAR(20)  NULL ,
  PRIMARY KEY (email))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table discount_statistic
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS discount_statistic (
  discount_statistic_id INT NOT NULL AUTO_INCREMENT ,
  discount_type VARCHAR(20) NOT NULL ,
  applied_count INT  NULL ,
  user_id INT  NULL ,
  PRIMARY KEY (discount_statistic_id),
  CONSTRAINT discount_statistic_id_fk
      FOREIGN KEY (user_id)
      REFERENCES user (user_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE)
ENGINE = InnoDB;

