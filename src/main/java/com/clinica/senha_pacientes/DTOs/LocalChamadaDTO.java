package com.clinica.senha_pacientes.DTOs;

import jakarta.validation.constraints.NotBlank;

public record LocalChamadaDTO(@NotBlank String localDeAtendimento) {
}
