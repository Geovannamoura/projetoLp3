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

-- USUÁRIOS
INSERT INTO usuario (nome, email) VALUES
('Geovanna Moura', 'geovanna.moura@email.com'),
('Lucas Silva', 'lucas.silva@email.com'),
('Mariana Costa', 'mariana.costa@email.com'),
('Pedro Oliveira', 'pedro.oliveira@email.com'),
('Ana Souza', 'ana.souza@email.com'),
('Rafael Lima', 'rafael.lima@email.com'),
('Beatriz Santos', 'beatriz.santos@email.com'),
('Carolina Ribeiro', 'carolina.ribeiro@email.com'),
('João Fernandes', 'joao.fernandes@email.com'),
('Camila Pereira', 'camila.pereira@email.com');

-- PROJETOS
INSERT INTO Projeto (nome_projeto, descricao_projeto) VALUES
('Sistema de Gestão de Tarefas', 'Desenvolvimento de um sistema web para controle de tarefas da equipe.'),
('Aplicativo de Finanças Pessoais', 'App mobile para registrar gastos e metas financeiras.'),
('Dashboard de Produtividade', 'Painel para acompanhar desempenho dos colaboradores.'),
('Portal de Clientes', 'Sistema online para suporte e acompanhamento de chamados.'),
('API de Integração ClickUp', 'Integração entre ClickUp e o sistema interno da empresa.'),
('Site Institucional', 'Criação do novo site da empresa em Angular.'),
('Sistema de RH', 'Plataforma para controle de ponto e férias.'),
('Gerenciador de Projetos TaskUp', 'Ferramenta para organizar sprints e backlog.'),
('Aplicativo de Delivery', 'App para entrega de alimentos com geolocalização.'),
('Painel de Relatórios Financeiros', 'Dashboard com gráficos e métricas financeiras.');

-- CATEGORIAS
INSERT INTO categoria (nome_categoria) VALUES
('Back-end'),
('Front-end'),
('Banco de Dados'),
('Design'),
('DevOps'),
('Testes'),
('Documentação'),
('Segurança'),
('Infraestrutura'),
('Planejamento');

-- TAREFAS
INSERT INTO tarefa (titulo_tarefa, descricao_tarefa, projetoId, responsavelId, categoriaId, criacao, prazo) VALUES
('Criar API de autenticação', 'Desenvolver endpoints de login e registro de usuários.', 1, 1, 1, GETDATE(), '2025-11-10'),
('Montar interface principal', 'Criar layout da página inicial com HTML e CSS.', 1, 2, 2, GETDATE(), '2025-11-15'),
('Modelar banco de dados', 'Definir estrutura das tabelas e relacionamentos.', 2, 3, 3, GETDATE(), '2025-11-20'),
('Prototipar tela de login', 'Criar protótipo no Figma da tela de login.', 3, 4, 4, GETDATE(), '2025-11-05'),
('Configurar pipeline CI/CD', 'Montar pipeline de deploy automático no Azure.', 5, 5, 5, GETDATE(), '2025-11-25'),
('Criar testes unitários', 'Implementar testes para funções principais.', 1, 6, 6, GETDATE(), '2025-11-12'),
('Documentar endpoints', 'Registrar rotas e exemplos de uso da API.', 1, 7, 7, GETDATE(), '2025-11-14'),
('Implementar autenticação OAuth', 'Adicionar login com Google e Microsoft.', 5, 8, 1, GETDATE(), '2025-11-30'),
('Criar layout responsivo', 'Ajustar design para mobile e desktop.', 6, 9, 2, GETDATE(), '2025-11-18'),
('Criar backup automatizado', 'Configurar backup diário no Azure Storage.', 9, 10, 9, GETDATE(), '2025-11-22'),
('Desenhar identidade visual', 'Criar novo logo e paleta de cores.', 6, 4, 4, GETDATE(), '2025-11-06'),
('Criar modelo de dados de RH', 'Montar estrutura de funcionários e férias.', 7, 2, 3, GETDATE(), '2025-11-16'),
('Criar script de seed inicial', 'Popular base de dados com valores padrão.', 2, 1, 3, GETDATE(), '2025-11-08'),
('Configurar servidor', 'Subir ambiente no Azure App Service.', 5, 3, 9, GETDATE(), '2025-11-09'),
('Montar tela de relatórios', 'Criar página de relatórios com filtros.', 10, 6, 2, GETDATE(), '2025-11-27'),
('Validar performance da API', 'Testar endpoints sob carga.', 1, 8, 6, GETDATE(), '2025-11-11'),
('Gerar documentação técnica', 'Escrever documentação final do projeto.', 8, 7, 7, GETDATE(), '2025-11-29'),
('Ajustar segurança da aplicação', 'Implementar criptografia e HTTPS.', 5, 9, 8, GETDATE(), '2025-11-24'),
('Fazer teste de usabilidade', 'Realizar teste com usuários reais.', 6, 10, 6, GETDATE(), '2025-11-23'),
('Planejar próxima sprint', 'Definir tarefas e metas para o próximo ciclo.', 8, 1, 10, GETDATE(), '2025-11-17'),
('Integrar ClickUp com banco interno', 'Fazer integração via API REST.', 5, 3, 1, GETDATE(), '2025-11-21'),
('Criar formulário de contato', 'Adicionar página de contato no site.', 6, 4, 2, GETDATE(), '2025-11-19'),
('Gerar relatórios automáticos', 'Relatórios semanais de produtividade.', 10, 5, 3, GETDATE(), '2025-11-28'),
('Auditar permissões', 'Revisar perfis de acesso dos usuários.', 7, 8, 8, GETDATE(), '2025-11-13'),
('Implementar login de administrador', 'Adicionar perfil admin no sistema.', 1, 2, 1, GETDATE(), '2025-11-26'),
('Criar script de deploy', 'Automatizar publicação via pipeline.', 5, 6, 5, GETDATE(), '2025-11-20'),
('Configurar servidor DNS', 'Apontar domínio para o site institucional.', 6, 9, 9, GETDATE(), '2025-11-21'),
('Criar cronograma do projeto', 'Planejar entregas semanais.', 8, 10, 10, GETDATE(), '2025-11-10'),
('Otimizar queries SQL', 'Melhorar performance do banco.', 2, 7, 3, GETDATE(), '2025-11-22'),
('Criar sistema de notificações', 'Notificar usuários sobre prazos.', 9, 1, 1, GETDATE(), '2025-11-25');


SELECT 
    t.id_tarefa,
    t.titulo_tarefa,
    u.nome AS responsavel,
    p.nome_projeto AS projeto,
    c.nome_categoria AS categoria,
    t.criacao,
    t.prazo
FROM tarefa t
JOIN usuario u ON t.responsavelId = u.responsavelId
JOIN Projeto p ON t.projetoId = p.projetoId
JOIN categoria c ON t.categoriaId = c.categoriaId
ORDER BY t.prazo;
