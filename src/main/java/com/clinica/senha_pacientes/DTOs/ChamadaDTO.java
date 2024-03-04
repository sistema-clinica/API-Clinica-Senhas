package com.clinica.senha_pacientes.DTOs;

import com.clinica.senha_pacientes.enitites.Paciente;

public record ChamadaDTO(String senha, String localDeAtendimento) {

    public ChamadaDTO(Paciente paciente) {
        this(paciente.getSenha(), paciente.getLocalDeAtendimento());
    }
}
