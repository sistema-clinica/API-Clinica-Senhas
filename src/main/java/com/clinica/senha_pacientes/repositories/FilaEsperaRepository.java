package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.filas.PacienteFilaEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilaEsperaRepository extends JpaRepository<PacienteFilaEspera, Long> {

    @Query("""
            select p.paciente
            from PacienteFilaEspera p
            order by p.dataDeAdicao limit 1
            """)
    Paciente findFirstPacienteByDataDeAdicao();

    void deleteByPaciente(Paciente paciente);
}
