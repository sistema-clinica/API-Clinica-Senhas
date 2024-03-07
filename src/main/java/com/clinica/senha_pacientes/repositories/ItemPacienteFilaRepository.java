package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.ItemPacienteFila;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.TipoFila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPacienteFilaRepository extends JpaRepository<ItemPacienteFila, Long> {
    @Query("""
        SELECT p.paciente
        FROM ItemPacienteFila p
        WHERE p.tipoFila = :tipoFila
        ORDER BY p.dataDeAdicao LIMIT 1
            """)
    Paciente findFirstPacienteByTipoFila(TipoFila tipoFila);

    @Query("""
            SELECT p FROM ItemPacienteFila p 
            WHERE p.tipoFila = com.clinica.senha_pacientes.enitites.TipoFila.HISTORICO
            ORDER BY p.dataDeAdicao DESC
            """)
    List<ItemPacienteFila> getHistorico();

    @Query("""
            SELECT p FROM ItemPacienteFila p 
            WHERE p.tipoFila = com.clinica.senha_pacientes.enitites.TipoFila.HISTORICO
            ORDER BY p.dataDeAdicao DESC LIMIT 1
            """)
    ItemPacienteFila getUltimoChamado();

    List<ItemPacienteFila> findAllByTipoFilaOrderByDataDeAdicao(TipoFila tipoFila);

    void deleteByPaciente(Paciente paciente);
}