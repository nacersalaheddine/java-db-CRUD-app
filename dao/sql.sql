CREATE DATABASE entities_application;
USE entities_application;
CREATE TABLE entities(id INT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL);

ALTER TABLE entities MODIFY COLUMN id INT auto_increment;
DESC entities;

INSERT INTO entities (name, email) VALUES ("Salah eddine Nacer","salaheddine05@gmail.com");
SELECT * FROM entities;


-- This is used to fix the timezone issue
SET GLOBAL time_zone = '+3:00';