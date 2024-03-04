package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Paciente;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@Getter
@Repository
public class AtendimentoRepository {
    private Queue<Paciente> filaNormalPaciente = new LinkedList<>();
    private Queue<Paciente> filaUrgentePaciente = new LinkedList<>();
    private Queue<Paciente> filaPreferencialPaciente = new LinkedList<>();
    private Queue<Paciente> filaEsperaPaciente = new LinkedList<>();
    private Stack<Paciente> historicoSenhasChamadas = new Stack<>();


    public void addSenhaHistorico(Paciente paciente) {
        historicoSenhasChamadas.add(paciente);
    }
    public void addPacienteFilaNormal(Paciente paciente) {
        filaNormalPaciente.add(paciente);
    }

    public void addPacienteFilaUrgente(Paciente paciente) {
        filaUrgentePaciente.add(paciente);
    }
    public void addPacienteFilaPreferencial(Paciente paciente) {
        filaPreferencialPaciente.add(paciente);
    }

    public void addPacienteFilaEspera(Paciente paciente) {
        filaEsperaPaciente.add(paciente);
    }

    public Paciente dequeuePacienteFilaNormal() {
        return filaNormalPaciente.remove();
    }

    public Paciente dequeuePacienteFilaUrgente() {
        return filaUrgentePaciente.remove();
    }
    public Paciente dequeuePacienteFilaPreferencial() {
        return filaPreferencialPaciente.remove();
    }

    public Paciente dequeuePacienteFilaEspera() {
        return filaEsperaPaciente.remove();
    }

    public boolean isFilaUrgenteVazia() {
        return filaUrgentePaciente.isEmpty();
    }

    public boolean isFilaNormalVazia() {
        return filaNormalPaciente.isEmpty();
    }
    public boolean isFilaPreferencialVazia() {
        return filaPreferencialPaciente.isEmpty();
    }

    public boolean isFilaEsperaVazia() {
        return filaEsperaPaciente.isEmpty();
    }
}
