DROP TABLE IF EXISTS card_queue;
DROP TABLE IF EXISTS team_creation_queue;
DROP TABLE IF EXISTS card_tag;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS org;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS user;


create table card(
   id INT AUTO_INCREMENT primary key,
   title VARCHAR(50) NOT NULL,
   description VARCHAR(300),
   image_url VARCHAR(200),
   hidden boolean default false,
   org_id int not null,
   created_by VARCHAR(50) not null,
   last_updated_by Varchar(100) not null
);

create table org(
   id INT AUTO_INCREMENT primary key,
   tribe_name VARCHAR(50) NOT NULL,
   team_name VARCHAR(50) NOT NULL
);


create table tags(
   id INT AUTO_INCREMENT primary key,
   value VARCHAR(50) NOT NULL
);


create table card_tag(
   id INT AUTO_INCREMENT primary key,
   cid INT not null,
   tid INT NOT NULL,
   FOREIGN KEY (tid) REFERENCES tags(id),
   FOREIGN KEY (cid) REFERENCES card(id)
);

create table card_queue(
   queue_id INT AUTO_INCREMENT primary key,
   cid INT,
   title VARCHAR(50),
   description VARCHAR(300),
   image_url VARCHAR(200),
   team_id int,
   hidden boolean,
   remove boolean,
   suggested_by VARCHAR(50)
);

create table team_creation_queue(
   id INT AUTO_INCREMENT primary key,
   team_name VARCHAR(50) not null,
   created_by varchar(50) not null,
   under_tribe varchar(50) not null
);

create table user(
   id INT AUTO_INCREMENT primary key,
   name VARCHAR(50) not null,
   org_id int not null,
   access_level int
);
