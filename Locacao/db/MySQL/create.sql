-- Create the database
CREATE DATABASE Locadora;

-- Switch to the newly created database
USE Locadora;

-- Create table usuariogeral
CREATE TABLE usuariogeral (
    cpf_cnpj VARCHAR(20) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    hierarquia INT NOT NULL,
    senha VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL
);

-- Create table usuariocliente
CREATE TABLE usuariocliente (
    cpf VARCHAR(20) PRIMARY KEY,
    data_de_nascimento DATE NOT NULL,
    sexo VARCHAR(20),
    telefone VARCHAR(20)
);

-- Create table usuariolocadora
CREATE TABLE usuariolocadora (
    cnpj VARCHAR(20) PRIMARY KEY,
    cidade VARCHAR(100)
);

-- Create table locacoes
CREATE TABLE locacoes (
    cnpj VARCHAR(20) REFERENCES usuariolocadora(cnpj),
    cpf VARCHAR(20) REFERENCES usuariocliente(cpf),
    horadevolucao DATETIME,
    horalocacao DATETIME,
    PRIMARY KEY (cnpj, cpf)
);
