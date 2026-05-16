CREATE TYPE tipo_agente AS ENUM ('CAMPO', 'SUPERVISOR', 'COORDENADOR');
CREATE TYPE status_visita AS ENUM ('TRABALHADO', 'RECUPERADO', 'FECHADO', 'RECUSADO');
CREATE TYPE tipo_imovel AS ENUM ('RESIDENCIA', 'COMERCIO', 'TERRENO_BALDIO', 'OUTRO');
CREATE TYPE tipo_deposito AS ENUM ('A1', 'A2', 'B', 'C', 'D1', 'D2', 'E');
CREATE TYPE categoria_localidade AS ENUM ('BR', 'PV', 'ST', 'FZ');
CREATE TYPE tipo_localidade AS ENUM ('SEDE', 'OUTRO');

CREATE TABLE agente (
    id BIGSERIAL PRIMARY KEY,
    cpf CHAR(11) UNIQUE NOT NULL,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    tipo tipo_agente NOT NULL DEFAULT 'CAMPO',
    supervisor_id BIGINT REFERENCES agente(id) ON DELETE SET NULL
);

CREATE TABLE localidade (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    categoria categoria_localidade NOT NULL,
    tipo tipo_localidade NOT NULL
);

CREATE TABLE area (
    id BIGSERIAL PRIMARY KEY,
    num_area VARCHAR(20) UNIQUE NOT NULL,
    agente_responsavel_id BIGINT REFERENCES agente(id) -- Relacionamento E_RESPONSAVEL
);

CREATE TABLE quarteirao (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    sequencia INTEGER DEFAULT 0,
    localidade_id BIGINT REFERENCES localidade(id) NOT NULL,
    area_id BIGINT REFERENCES area(id) ON DELETE SET NULL
);

CREATE TABLE lado (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    quarteirao_id BIGINT NOT NULL REFERENCES quarteirao(id)
);

CREATE TABLE imovel (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(10) NOT NULL,
    sequencia INTEGER,
    num_residentes INTEGER DEFAULT 0,
    num_caes INTEGER DEFAULT 0,
    num_gatos INTEGER DEFAULT 0,
    tipo tipo_imovel NOT NULL,
    lado_id BIGINT REFERENCES lado(id),
    localidade_id BIGINT REFERENCES localidade(id)
);

CREATE TABLE ciclo (
    id BIGSERIAL PRIMARY KEY,
    numero_ciclo INTEGER NOT NULL,
    ano INTEGER NOT NULL,
    UNIQUE(numero_ciclo, ano)
);

CREATE TABLE visita (
    id BIGSERIAL PRIMARY KEY,
    data_visita TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacao TEXT,
    imovel_id BIGINT NOT NULL REFERENCES imovel(id),
    agente_id BIGINT NOT NULL REFERENCES agente(id),
    ciclo_id BIGINT NOT NULL REFERENCES ciclo(id)
);

CREATE TABLE tratamento (
    visita_id BIGINT PRIMARY KEY REFERENCES visita(id) ON DELETE CASCADE,
    status status_visita NOT NULL,
    qntd_eliminados INTEGER DEFAULT 0,
    qntd_tratados INTEGER DEFAULT 0,
    qntd_larvicida NUMERIC(10,2),
    tipo_larvicida VARCHAR(50),
    focal BOOLEAN DEFAULT FALSE
);

-- LIRAa: Dados específicos do Levantamento de Índice Rápido
CREATE TABLE liraa (
    visita_id BIGINT PRIMARY KEY REFERENCES visita(id) ON DELETE CASCADE
);

-- Foco: Amostras coletadas (Relacionado ao LIRAa ou Visita)
CREATE TABLE foco (
    id BIGSERIAL PRIMARY KEY,
    visita_id BIGINT NOT NULL REFERENCES visita(id) ON DELETE CASCADE,
    tipo_deposito tipo_deposito NOT NULL,
    numero_tubito VARCHAR(50),
    resultado_laboratorio VARCHAR(100) -- Pode ser preenchido depois
);


-- Agiliza a busca de imóveis por logradouro/lado
CREATE INDEX idx_imovel_lado ON imovel(lado_id);
-- Agiliza a busca de visitas por ciclo (essencial para relatórios de fechamento)
CREATE INDEX idx_visita_ciclo ON visita(ciclo_id);
-- Agiliza a busca de histórico de um imóvel específico
CREATE INDEX idx_visita_imovel ON visita(imovel_id);
-- Agiliza a busca de produtividade de um agente
CREATE INDEX idx_visita_agente ON visita(agente_id);


-- Em vez de salvar o número de imóveis na tabela Quarteirão (que pode desatualizar),
-- usamos esta View para ter o dado sempre correto:
CREATE VIEW vw_resumo_quarteirao AS
SELECT q.id, q.numero, COUNT(i.id) as total_imoveis
FROM quarteirao q
LEFT JOIN lado l ON l.quarteirao_id = q.id
LEFT JOIN imovel i ON i.lado_id = l.id
GROUP BY q.id, q.numero;