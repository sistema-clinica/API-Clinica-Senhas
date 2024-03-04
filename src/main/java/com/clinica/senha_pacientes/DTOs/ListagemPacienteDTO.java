package com.clinica.senha_pacientes.DTOs;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Status;
import com.clinica.senha_pacientes.enitites.Urgencia;

public record ListagemPacienteDTO(Long id, String senha, String nome, Urgencia urgencia, Status status) {

    public ListagemPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getSenha(), paciente.getNome(), paciente.getUrgencia(), paciente.getStatus());
    }
}
