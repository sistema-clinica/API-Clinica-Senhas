package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.ItemPacienteFila;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.TipoFila;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Repository
public class AtendimentoRepository {

    @Autowired
    private ItemPacienteFilaRepository itemPacienteFilaRepository;

    public Queue<Paciente> getFilaUrgentePaciente() {
        return new LinkedList<>(itemPacienteFilaRepository.findPacienteByTipoFilaOrderByDataDeAdicao(TipoFila.URGENTE));
    }

    public Queue<Paciente> getFilaNormalPaciente() {
        return new LinkedList<>(itemPacienteFilaRepository.findPacienteByTipoFilaOrderByDataDeAdicao(TipoFila.NORMAL));

    }

    public Queue<Paciente> getFilaPreferencialPaciente() {
        return new LinkedList<>(itemPacienteFilaRepository.findPacienteByTipoFilaOrderByDataDeAdicao(TipoFila.PREFERENCIAL));

    }


    public Queue<Paciente> getFilaEsperaPaciente() {
        return new LinkedList<>(itemPacienteFilaRepository.findPacienteByTipoFilaOrderByDataDeAdicao(TipoFila.ESPERA));
    }


    public List<Paciente> getHistoricoSenhasChamadas() {
        return itemPacienteFilaRepository.getHistorico(Limit.of(7)).stream().map(ItemPacienteFila::getPaciente).toList();
    }

    public void addSenhaHistorico(Paciente paciente) {
        itemPacienteFilaRepository.save(new ItemPacienteFila(paciente, TipoFila.HISTORICO));
    }
    public void addPacienteFilaNormal(Paciente paciente) {
        itemPacienteFilaRepository.save(new ItemPacienteFila(paciente,TipoFila.NORMAL));
    }

    public void addPacienteFilaUrgente(Paciente paciente) {
        itemPacienteFilaRepository.save(new ItemPacienteFila(paciente,TipoFila.URGENTE));
    }
    public void addPacienteFilaPreferencial(Paciente paciente) {
        itemPacienteFilaRepository.save(new ItemPacienteFila(paciente,TipoFila.PREFERENCIAL));
    }

    public void addPacienteFilaEspera(Paciente paciente) {
        itemPacienteFilaRepository.save(new ItemPacienteFila(paciente,TipoFila.ESPERA));
    }

    @Transactional
    public Paciente dequeuePacienteFilaNormal() {
        var paciente = itemPacienteFilaRepository.findFirstPacienteByTipoFila(TipoFila.NORMAL);
        itemPacienteFilaRepository.deleteByPaciente(paciente);
        return paciente;
    }

    @Transactional
    public Paciente dequeuePacienteFilaUrgente() {
        var paciente = itemPacienteFilaRepository.findFirstPacienteByTipoFila(TipoFila.URGENTE);
        itemPacienteFilaRepository.deleteByPaciente(paciente);
        return paciente;

    }
    @Transactional
    public Paciente dequeuePacienteFilaPreferencial() {
        var paciente = itemPacienteFilaRepository.findFirstPacienteByTipoFila(TipoFila.PREFERENCIAL);
        itemPacienteFilaRepository.deleteByPaciente(paciente);
        return paciente;
    }
    @Transactional
    public Paciente dequeuePacienteFilaEspera() {
        var paciente = itemPacienteFilaRepository.findFirstPacienteByTipoFila(TipoFila.ESPERA);
        itemPacienteFilaRepository.deleteByPaciente(paciente);
        return paciente;
    }

    public boolean isFilaEsperaVazia() {
        return itemPacienteFilaRepository.findPacienteByTipoFilaOrderByDataDeAdicao(TipoFila.ESPERA).isEmpty();
    }
}
