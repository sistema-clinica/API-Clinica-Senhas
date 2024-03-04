package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Urgencia;
import com.clinica.senha_pacientes.repositories.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository repository;
    private int countPacientesUrgentes = 0;
    private int countPacientesPreferenciais = 0;
    private final int maxCountPacientesUrgentes = 3;
    private final int maxCountPacientesPreferenciais = 2;

    public void addPacienteFila(Paciente paciente) {
        repository.addSenhaHistorico(paciente);
        switch (paciente.getUrgencia()) {
            case NORMAL -> repository.addPacienteFilaNormal(paciente);
            case URGENTE -> repository.addPacienteFilaUrgente(paciente);
            case PREFERENCIAL -> repository.addPacienteFilaPreferencial(paciente);
        }
    }

    public void addPacienteFilaEspera(Paciente paciente) {
        repository.addPacienteFilaEspera(paciente);
    }

    public Paciente dequeuePaciente() {
        if (countPacientesPreferenciais < maxCountPacientesPreferenciais && !repository.isFilaPreferencialVazia()) {
            countPacientesPreferenciais++;
            Paciente paciente = repository.dequeuePacienteFilaPreferencial();

            repository.addSenhaHistorico(paciente);
            paciente.foiAtendido();

            return paciente;
        } else if (countPacientesUrgentes < maxCountPacientesUrgentes && !repository.isFilaUrgenteVazia()) {
            countPacientesPreferenciais = 0;
            countPacientesUrgentes++;
            Paciente paciente = repository.dequeuePacienteFilaUrgente();

            repository.addSenhaHistorico(paciente);
            paciente.foiAtendido();

            return paciente;
        } else if (!repository.isFilaNormalVazia()) {
            countPacientesPreferenciais = 0;
            countPacientesUrgentes = 0;
            Paciente paciente = repository.dequeuePacienteFilaNormal();

            repository.addSenhaHistorico(paciente);
            paciente.foiAtendido();

            return paciente;
        }
        throw new RuntimeException("Não existem pacientes na fila de atendimento");
    }


    public Queue<Paciente> listarFilaAtendimento() {
        Queue<Paciente> filaUrgenteCopy = new LinkedList<>(repository.getFilaUrgentePaciente());
        Queue<Paciente> filaNormalCopy = new LinkedList<>(repository.getFilaNormalPaciente());
        Queue<Paciente> filaPreferencialCopy = new LinkedList<>(repository.getFilaPreferencialPaciente());
        Queue<Paciente> filaFinal = new LinkedList<>();
        int copyCountPacientesUrgentes = countPacientesUrgentes;
        int copyCountPacientesPreferenciais = countPacientesPreferenciais;

        while (!filaPreferencialCopy.isEmpty()) {
            filaFinal.add(filaPreferencialCopy.poll());
            copyCountPacientesPreferenciais++;
            if (copyCountPacientesPreferenciais == maxCountPacientesPreferenciais && !filaNormalCopy.isEmpty()) {
                filaFinal.add(filaNormalCopy.poll());
                copyCountPacientesPreferenciais = 0;
            }
        }

        while (!filaUrgenteCopy.isEmpty()) {
            filaFinal.add(filaUrgenteCopy.poll());
            copyCountPacientesUrgentes++;
            if (copyCountPacientesUrgentes == maxCountPacientesUrgentes && !filaNormalCopy.isEmpty()) {
                filaFinal.add(filaNormalCopy.poll());
                copyCountPacientesUrgentes = 0;
            }
        }

        while (!filaNormalCopy.isEmpty()) {
            filaFinal.add(filaNormalCopy.poll());
        }

        return filaFinal;

    }

    public Paciente dequeuePacienteEspera() {
        if (!repository.isFilaEsperaVazia()) {
            Paciente paciente = repository.dequeuePacienteFilaEspera();
            paciente.emTriagem();
            repository.addSenhaHistorico(paciente);

            return paciente;
        }
        throw new RuntimeException("Não existem pacientes na fila de espera");
    }

    public Queue<Paciente> listarFilaEspera() {
        return repository.getFilaEsperaPaciente();
    }

    public List<Paciente> getHistoricoSenhasChamadas(int numero_limit) {
        Stack<Paciente> senhasChamadas = repository.getHistoricoSenhasChamadas();
        List<Paciente> listaRetorno = new ArrayList<>();
        for (int i = senhasChamadas.size() - 1; i >= 0; i--) {
            listaRetorno.add(senhasChamadas.get(i));
        }
        return listaRetorno.stream().limit(numero_limit).toList();
    }

    public Paciente getUltimaSenhaChamada() {
        return repository.getHistoricoSenhasChamadas().peek();
    }

    public List<Paciente> getPacientesByUrgencia(Urgencia urgencia) {
        return this.listarFilaAtendimento().stream().filter((p) -> p.filterUrgencia(urgencia)).toList();
    }
}
