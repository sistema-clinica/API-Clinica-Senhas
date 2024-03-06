package com.clinica.senha_pacientes.enitites.filas;


import com.clinica.senha_pacientes.enitites.Paciente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historico_senhas_chamadas")
@NoArgsConstructor
public class HistoricoSenhasChamadas extends ItemPacienteFila {
    public HistoricoSenhasChamadas(Paciente paciente) {
        super(paciente);
    }
}
