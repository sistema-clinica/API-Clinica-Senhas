package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.filas.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

@Getter
@Repository
public class AtendimentoRepository {

    @Autowired
    private FilaEsperaRepository filaEsperaRepository;

    @Autowired
    private FilaPreferencialRepository filaPreferencialRepository;

    @Autowired
    private FilaNormalRepository filaNormalRepository;

    @Autowired
    private FilaUrgenteRepository filaUrgenteRepository;

    @Autowired
    private HistorcoSenhasRepository historcoSenhasRepository;

    public Queue<Paciente> getFilaUrgentePaciente() {
        return new LinkedList<>(filaUrgenteRepository.findAll().stream().map(ItemPacienteFila::getPaciente).toList());
    }

    public Queue<Paciente> getFilaNormalPaciente() {
        return new LinkedList<>(filaNormalRepository.findAll().stream().map(ItemPacienteFila::getPaciente).toList());
    }

    public Queue<Paciente> getFilaPreferencialPaciente() {
        return new LinkedList<>(filaPreferencialRepository.findAll().stream().map(ItemPacienteFila::getPaciente).toList());
    }


    public Queue<Paciente> getFilaEsperaPaciente() {
        return new LinkedList<>(filaEsperaRepository.findAll().stream().map(ItemPacienteFila::getPaciente).toList());
    }


    public Stack<Paciente> getHistoricoSenhasChamadas() {
        return historcoSenhasRepository.findAll().stream().map(ItemPacienteFila::getPaciente).collect(Collectors.toCollection(Stack::new));
    }

    public void addSenhaHistorico(Paciente paciente) {
        historcoSenhasRepository.save(new HistoricoSenhasChamadas(paciente));
    }
    public void addPacienteFilaNormal(Paciente paciente) {
        filaNormalRepository.save(new PacienteFilaNormal(paciente));
    }

    public void addPacienteFilaUrgente(Paciente paciente) {
        filaUrgenteRepository.save(new PacienteFilaUrgente(paciente));
    }
    public void addPacienteFilaPreferencial(Paciente paciente) {
        filaPreferencialRepository.save(new PacienteFilaPreferencial(paciente));
    }

    public void addPacienteFilaEspera(Paciente paciente) {
        PacienteFilaEspera pacienteEspera = new PacienteFilaEspera(paciente);
        System.out.println(pacienteEspera.getDataDeAdicao());
        filaEsperaRepository.save(new PacienteFilaEspera(paciente));
    }

    @Transactional
    public Paciente dequeuePacienteFilaNormal() {
        Paciente paciente = filaNormalRepository.findFirstPacienteByDataDeAdicao();
        filaNormalRepository.deleteByPaciente(paciente);
        return paciente;
    }

    @Transactional
    public Paciente dequeuePacienteFilaUrgente() {
        Paciente paciente = filaUrgenteRepository.findFirstPacienteByDataDeAdicao();
        filaUrgenteRepository.deleteByPaciente(paciente);
        return paciente;

    }
    @Transactional
    public Paciente dequeuePacienteFilaPreferencial() {
        Paciente paciente = filaPreferencialRepository.findFirstPacienteByDataDeAdicao();
        filaPreferencialRepository.deleteByPaciente(paciente);
        return paciente;
    }
    @Transactional
    public Paciente dequeuePacienteFilaEspera() {
        Paciente paciente = filaEsperaRepository.findFirstPacienteByDataDeAdicao();
        filaEsperaRepository.deleteByPaciente(paciente);
        return paciente;
    }

    public boolean isFilaEsperaVazia() {
        return filaEsperaRepository.findAll().isEmpty();
    }
}
