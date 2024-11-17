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

CREATE TABLE receitaEstoque (
 estoqueId 		INT			NOT NULL,
 receitaId		INT 		NOT NULL,
 PRIMARY KEY(estoqueId, receitaId),
 FOREIGN KEY(estoqueId) REFERENCES estoque(id),
 FOREIGN KEY(receitaId) REFERENCES receita(id)
);

INSERT INTO estoque (id, medicamento, quantidade, fornecedor, funcionarioRegistro)
VALUES (0, 'Loratadina', 1, 'Gov', 1);

 INSERT INTO estoque (id, medicamento, quantidade, fornecedor, funcionarioRegistro)
 VALUES (0, 'Dipirona', 2, 'Generico', 2);