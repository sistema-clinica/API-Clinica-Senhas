package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsBySenha(String senha);
    Paciente findPacienteBySenha(String senha);

    List<Paciente> findPacientesByStatus(Status status);
}
