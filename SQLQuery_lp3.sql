CREATE DATABASE AtividadeLP3

-- Criação da tabela de usuários
CREATE TABLE usuario (
    responsavelId INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Criação da tabela de projetos
CREATE TABLE Projeto (
    projetoId INT IDENTITY(1,1) PRIMARY KEY,
    nome_projeto VARCHAR(150) NOT NULL,
    descricao_projeto VARCHAR(MAX)
);

-- Criação da tabela de categorias
CREATE TABLE categoria (
    categoriaId INT IDENTITY(1,1) PRIMARY KEY,
    nome_categoria VARCHAR(100) NOT NULL
);

-- Criação da tabela de tarefas
CREATE TABLE tarefa (
    id_tarefa INT IDENTITY(1,1) PRIMARY KEY,
    titulo_tarefa VARCHAR(150) NOT NULL,
    descricao_tarefa VARCHAR(MAX),
    projetoId INT,
    responsavelId INT,
    categoriaId INT,
    criacao DATE DEFAULT GETDATE(),
    prazo DATE,
    FOREIGN KEY (projetoId) REFERENCES Projeto(projetoId)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (responsavelId) REFERENCES usuario(responsavelId)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (categoriaId) REFERENCES categoria(categoriaId)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
