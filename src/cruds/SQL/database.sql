---- MariaDB/MySQL

CREATE DATABASE hospitaldb;

USE hospitaldb;


CREATE TABLE estoque (
  id 			INT 			NOT NULL 	AUTO_INCREMENT,
  medicamento	VARCHAR(40) 	NOT NULL,
  quantidade 	INT 			NOT NULL,
  fornecedor	VARCHAR(40) 	NOT NULL,
  funcionarioRegistro	INT		NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE receita (
 id					INT 		NOT NULL	AUTO_INCREMENT,
 dataReceita		DATE		NOT NULL,
 medicoCRM			VARCHAR(20)	NOT NULL,
 PRIMARY KEY(id)
);

CREATE TABLE receita_estoque (
 estoqueId 		INT			NOT NULL,
 receitaId		INT 		NOT NULL,
 PRIMARY KEY(estoqueId, receitaId),
 FOREIGN KEY(estoqueId) REFERENCES estoque(id),
 FOREIGN KEY(receitaId) REFERENCES receita(id)
);