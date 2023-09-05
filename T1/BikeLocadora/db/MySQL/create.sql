drop database if exists BikeLocacao;
create database BikeLocacao;

use BikeLocacao;

create table Pessoa(
    id bigint not null auto_increment,
    cpf varchar(18) unique not null,
    nome varchar(40) not null,
    telefone varchar(20),
    sexo char(1),
    data_nascimento date,
    email varchar(30),
    senha varchar(15),
    cliente_admin int,
    primary key (id));
    

create table Locadora(
    id bigint not null auto_increment,
    cnpj bigint unique not null,
    email varchar(30),
    senha varchar(15) not null,
    nome varchar(30),
    cidade varchar(200),
    primary key (id));
    

create table Locacao(
    id bigint not null auto_increment,
    locadora_cnpj bigint not null,
    pessoa_cpf varchar(18) not null,
    data_locacao date,
    horario_locacao time,
    ativo int,
    primary key (id));

insert into Locadora(cnpj, email, senha, nome, cidade) values (11111111111, 'locadora1@gmail.com.br', 'abc123', 'Locadora 1', 'São Carlos');
insert into Locadora(cnpj, email, senha, nome, cidade) values (22222222222, 'locadora2@gmail.com.br', 'abc123', 'Locadora 2', 'São Paulo');
insert into Locadora(cnpj, email, senha, nome, cidade) values (33333333333, 'locadora3@gmail.com.br', 'abc123', 'Locadora 3', 'Campinas');
insert into Locadora(cnpj, email, senha, nome, cidade) values (44444444444, 'locadora4@gmail.com.br', 'abc123', 'Locadora 4', 'São Carlos');
insert into Locadora(cnpj, email, senha, nome, cidade) values (55555555555, 'locadora5@gmail.com.br', 'abc123', 'Locadora 5', 'Campinas');
    
insert into Pessoa(cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) values ('111111111', 'Administrador', '00900000000', 'O', '2000-01-01', 'admin@gmail.com', 'abc123', 1);
insert into Pessoa(cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) values ('222222222', 'Joao', '11911111111', 'M', '1990-05-08', 'joao@gmail.com', 'abc123', 0);
insert into Pessoa(cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) values ('333333333', 'Maria', '22922222222', 'F', '1995-06-09', 'maria@gmail.com', 'abc123', 0);
insert into Pessoa(cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) values ('444444444', 'Jose', '33933333333', 'M', '1999-07-10', 'jose@gmail.com', 'abc123', 0);
insert into Pessoa(cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) values ('555555555', 'Gabriela', '44944444444', 'F', '2000-08-11', 'gabriela@gmail.com', 'abc123', 0); 
          

insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(11111111111, '111111111', '2023-09-01', '12:00:00', 1);
insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(22222222222, '111111111', '2023-09-01', '12:00:00', 1);
insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(33333333333, '222222222', '2023-09-01', '10:00:00', 0);
insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(44444444444, '222222222', '2023-09-01', '12:00:00', 1);
insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(55555555555, '333333333', '2023-09-01', '12:00:00', 1);
insert into Locacao(locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo) values(44444444444, '444444444', '2023-09-01', '12:00:00', 1);  
    
