-- -----------------------------------------------------
-- Tabela USUÁRIO
-- -----------------------------------------------------
CREATE TABLE USUÁRIO (
  Email VARCHAR(200) PRIMARY KEY,
  Nome VARCHAR(300) NOT NULL,
  Telefone VARCHAR(15) NOT NULL,
  Senha VARCHAR(50) NOT NULL,
  CEP VARCHAR(10) NOT NULL,
  Cidade VARCHAR(50) NOT NULL,
  Rua VARCHAR(50) NOT NULL,
  Número INTEGER NOT NULL,
  Complemento VARCHAR(300)
);


-- -----------------------------------------------------
-- Tabela ADMINISTRADORES
-- -----------------------------------------------------
CREATE TABLE ADMINISTRADORES (
  Nome VARCHAR(300) NOT NULL,
  Senha VARCHAR(50) NOT NULL,
  PRIMARY KEY (Nome)
);


-- -----------------------------------------------------
-- Tabela CATEGORIA
-- -----------------------------------------------------
CREATE TABLE CATEGORIA (
  Id serial PRIMARY KEY,
  Nome VARCHAR(300) NOT NULL UNIQUE
);


-- -----------------------------------------------------
-- Tabela TUTORIAL
-- -----------------------------------------------------
CREATE TABLE TUTORIAL (
  Id serial PRIMARY KEY,
  Título VARCHAR(300) NOT NULL,
  Texto TEXT,
  URL VARCHAR(500) NOT NULL,
  CatId serial NOT NULL,
  CONSTRAINT FK_CatId
    FOREIGN KEY (CatId)
    REFERENCES CATEGORIA (Id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
