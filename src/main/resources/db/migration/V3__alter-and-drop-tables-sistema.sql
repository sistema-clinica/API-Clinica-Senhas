CREATE TABLE IF NOT EXISTS item_paciente_fila (
    id BIGINT AUTO_INCREMENT NOT NULL,
    paciente_id BIGINT NOT NULL,
    tipo_fila VARCHAR(255) NOT NULL,
    data_de_adicao DATETIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

DROP TABLE paciente_fila_normal;
DROP TABLE paciente_fila_urgente;
DROP TABLE paciente_fila_preferencial;
DROP TABLE paciente_fila_espera;
DROP TABLE historico_senhas_chamadas;
