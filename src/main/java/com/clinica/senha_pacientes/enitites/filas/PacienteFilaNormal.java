package com.clinica.senha_pacientes.enitites.filas;

import com.clinica.senha_pacientes.enitites.Paciente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente_fila_normal")
@NoArgsConstructor
public class PacienteFilaNormal extends ItemPacienteFila {
    public PacienteFilaNormal(Paciente paciente) {
        super(paciente);
    }
}
