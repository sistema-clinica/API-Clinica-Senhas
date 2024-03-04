package com.clinica.senha_pacientes.DTOs;

import com.clinica.senha_pacientes.enitites.Urgencia;
import jakarta.validation.constraints.NotBlank;

public record PacienteTriagemDTO(

        @NotBlank
        String senha,

        @NotBlank
        Urgencia urgencia,

        @NotBlank
        String detalheSintomas) {
}
