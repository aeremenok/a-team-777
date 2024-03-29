CREATE TABLE etu.db_artist (artistID INT NOT NULL AUTO_INCREMENT, birthdate DATETIME NULL, country VARCHAR(10) NULL, description VARCHAR(255) NULL, imageUrl VARCHAR(255) NULL, name VARCHAR(30) NULL, picture LONGBLOB NULL, PRIMARY KEY (artistID)) ENGINE=InnoDB
;

CREATE TABLE etu.db_museum (description VARCHAR(255) NULL, imageURL VARCHAR(255) NULL, museumID INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NULL, picture LONGBLOB NULL, www VARCHAR(255) NULL, PRIMARY KEY (museumID)) ENGINE=InnoDB
;

CREATE TABLE etu.db_genre (Name VARCHAR(255) NULL, descrption VARCHAR(255) NULL, genreID INT NOT NULL AUTO_INCREMENT, imageURL VARCHAR(255) NULL, picture LONGBLOB NULL, PRIMARY KEY (genreID)) ENGINE=InnoDB
;

CREATE TABLE etu.db_masterpiece (ID_artist INT NOT NULL, ID_genre INT NULL, ID_museum INT NULL, ID_type INT NOT NULL, creationYear INT NULL, description VARCHAR(255) NULL, height INT NULL, imageURL VARCHAR(255) NULL, mass INT NULL, masterpieceID INT NOT NULL AUTO_INCREMENT, material VARCHAR(100) NULL, photo VARCHAR(255) NULL, picture LONGBLOB NULL, title VARCHAR(255) NULL, width INT NULL, PRIMARY KEY (masterpieceID), KEY (ID_artist), KEY (ID_genre), KEY (ID_museum)) ENGINE=InnoDB
;

ALTER TABLE etu.db_masterpiece ADD FOREIGN KEY (ID_artist) REFERENCES etu.db_artist (artistID)
;

ALTER TABLE etu.db_masterpiece ADD FOREIGN KEY (ID_genre) REFERENCES etu.db_genre (genreID)
;

ALTER TABLE etu.db_masterpiece ADD FOREIGN KEY (ID_museum) REFERENCES etu.db_museum (museumID)
;

CREATE TABLE AUTO_PK_SUPPORT (  TABLE_NAME CHAR(100) NOT NULL,  NEXT_ID INTEGER NOT NULL, UNIQUE (TABLE_NAME))
;

DELETE FROM AUTO_PK_SUPPORT WHERE TABLE_NAME IN ('db_artist', 'db_genre', 'db_masterpiece', 'db_museum')
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('db_artist', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('db_genre', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('db_masterpiece', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('db_museum', 200)
;

