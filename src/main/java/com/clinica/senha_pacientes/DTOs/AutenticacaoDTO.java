package com.clinica.senha_pacientes.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(

        @NotBlank
        String username,

        @NotBlank
        String senha) {
}
