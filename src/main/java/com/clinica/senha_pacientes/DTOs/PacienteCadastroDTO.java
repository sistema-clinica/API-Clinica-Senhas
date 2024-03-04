package com.clinica.senha_pacientes.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PacienteCadastroDTO(
        @NotBlank
        String nome,
        @Past
        @JsonAlias("dd/MM/yyyy")
        @NotBlank
        LocalDate dataNasc,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf) {
}
