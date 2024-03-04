package com.clinica.senha_pacientes.DTOs;

import com.clinica.senha_pacientes.enitites.Paciente;

public record RetornoCadastroPacienteDTO(Long id, String senha, String nome) {

    public RetornoCadastroPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getSenha(), paciente.getNome());
    }
}