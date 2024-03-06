package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.filas.HistoricoSenhasChamadas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorcoSenhasRepository extends JpaRepository<HistoricoSenhasChamadas, Long> {
}
