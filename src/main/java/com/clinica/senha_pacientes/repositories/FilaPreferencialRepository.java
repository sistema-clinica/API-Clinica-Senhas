package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.filas.PacienteFilaPreferencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilaPreferencialRepository extends JpaRepository<PacienteFilaPreferencial, Long> {

    @Query("""
        select p.paciente 
        from PacienteFilaPreferencial p
        order by p.dataDeAdicao limit 1
            """)
    Paciente findFirstPacienteByDataDeAdicao();

    void deleteByPaciente(Paciente paciente);
}