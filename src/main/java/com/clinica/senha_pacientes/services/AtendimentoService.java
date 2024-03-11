package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Urgencia;
import com.clinica.senha_pacientes.repositories.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository repository;


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
        Queue<Paciente> filaAtendiemnto = this.listarFilaAtendimento();

        if (!filaAtendiemnto.isEmpty()) {
            Paciente paciente = filaAtendiemnto.poll();

            switch (paciente.getUrgencia()) {
                case NORMAL -> repository.dequeuePacienteFilaNormal();
                case URGENTE -> repository.dequeuePacienteFilaUrgente();
                case PREFERENCIAL -> repository.dequeuePacienteFilaPreferencial();
            }
            repository.addSenhaHistorico(paciente);
            return paciente;
        }

        throw new RuntimeException("Não Existem Pacientes na fila de atendimento");
    }


    public Queue<Paciente> listarFilaAtendimento() {
        Queue<Paciente> filaUrgente = repository.getFilaUrgentePaciente();
        Queue<Paciente> filaNormal = repository.getFilaNormalPaciente();
        Queue<Paciente> filaPreferencial = repository.getFilaPreferencialPaciente();
        Queue<Paciente> filaFinal = new LinkedList<>();

        int countUrgentes = 0;
        int countPreferenciais = 0;

        // A cada 2 pacientes urgentes atendidos, 1 paciente preferencial deve ser atendido.
        // A cada 2 pacientes preferenciais atendidos ou 3 pacientes urgentes atendidos, 1 paciente normal deve ser atendido.
        while (!filaUrgente.isEmpty()) {
            filaFinal.add(filaUrgente.poll());
            countUrgentes++;

            if (countUrgentes == 2 && !filaPreferencial.isEmpty()) {
                filaFinal.add(filaPreferencial.poll());
                countPreferenciais++;
            }

            if (countUrgentes == 3 && !filaNormal.isEmpty()) {
                filaFinal.add(filaNormal.poll());
                countUrgentes = 0;
            }
        }

        while (!filaPreferencial.isEmpty()) {

            if (countPreferenciais == 2 && !filaNormal.isEmpty()) {
                filaFinal.add(filaNormal.poll());
                countPreferenciais = 0;
            }

            filaFinal.add(filaPreferencial.poll());
            countPreferenciais++;
        }

        while (!filaNormal.isEmpty()) {
            filaFinal.add(filaNormal.poll());
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

    public List<Paciente> getHistoricoSenhasChamadas() {
        List<Paciente> senhasChamadas = repository.getHistoricoSenhasChamadas();
        if (!senhasChamadas.isEmpty()) {
            return senhasChamadas.subList(1, senhasChamadas.size());
        }
        throw new RuntimeException("Não existem pacientes anteriores");
    }

    public Paciente getUltimaSenhaChamada() {
        if (!getHistoricoSenhasChamadas().isEmpty()) {
            return repository.getHistoricoSenhasChamadas().get(0);
        }
        throw new RuntimeException("Não existem pacientes anteriores");
    }

    public List<Paciente> getPacientesByUrgencia(Urgencia urgencia) {
        return this.listarFilaAtendimento().stream().filter((p) -> p.filterUrgencia(urgencia)).toList();
    }
}
