package com.clinica.senha_pacientes.enitites.filas;

import com.clinica.senha_pacientes.enitites.Paciente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente_fila_espera")
@NoArgsConstructor
public class PacienteFilaEspera extends ItemPacienteFila {
    public PacienteFilaEspera(Paciente paciente) {
        super(paciente);
    }
}
