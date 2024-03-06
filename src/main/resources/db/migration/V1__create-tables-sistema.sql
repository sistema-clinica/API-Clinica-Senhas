CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nasc DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    local_de_atendimento VARCHAR(255),
    urgencia VARCHAR(20),
    detalhe_sintomas TEXT
);

CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS historico_senhas_chamadas (
    id BIGINT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

CREATE TABLE IF NOT EXISTS paciente_fila_normal (
    id BIGINT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

CREATE TABLE IF NOT EXISTS paciente_fila_espera (
    id BIGINT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

CREATE TABLE IF NOT EXISTS paciente_fila_urgente (
    id BIGINT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

CREATE TABLE IF NOT EXISTS paciente_fila_preferencial (
    id BIGINT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
