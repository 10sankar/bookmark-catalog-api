DROP TABLE IF EXISTS card;

create table card(
   id INT AUTO_INCREMENT,
   title VARCHAR(50) NOT NULL,
   description VARCHAR(300),
   image_url VARCHAR(200),
   PRIMARY KEY ( id )
);
