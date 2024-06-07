CREATE DATABASE IF NOT EXISTS `walletmod5` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `walletmod5`;

CREATE TABLE usuarios (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  balance decimal(10,2) NOT NULL
);

INSERT INTO usuarios VALUES (1, 'MARCELO CONTRERAS CRUZ', 0);
INSERT INTO usuarios VALUES (2, 'BRAULIO HERNANDEZ BARRERA', 0);

CREATE TABLE authusuarios (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_usuario int NOT NULL,
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(25) NOT NULL,
  constraint fk_usuarios foreign key (id_usuario) references usuarios(id)
);

INSERT INTO authusuarios VALUES (1, 1, 'marcelo', '123');
INSERT INTO authusuarios VALUES (2, 2, 'braulio', '123');