package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.DTOs.PacienteCadastroDTO;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Status;
import com.clinica.senha_pacientes.enitites.Urgencia;
import com.clinica.senha_pacientes.repositories.AtendimentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AtendimentoServiceTest {

    @Mock
    private AtendimentoRepository repository;

    @InjectMocks
    private AtendimentoService atendimentoService;

    @Test
    @DisplayName("Verifica se a fila de pacientes está na ordem correta de acordo com a urgência do paciente e lógica criada")
    public void testListarFilaAtendimento() {

        Queue<Paciente> filaUrgente = new LinkedList<>();
        filaUrgente.offer(new Paciente(new PacienteCadastroDTO("P1", LocalDate.now().minusYears(2), "00000000001")));
        filaUrgente.offer(new Paciente(new PacienteCadastroDTO("P2", LocalDate.now().minusYears(2), "00000000001")));
        filaUrgente.offer(new Paciente(new PacienteCadastroDTO("P3", LocalDate.now().minusYears(2), "00000000001")));

        Queue<Paciente> filaNormal = new LinkedList<>();
        filaNormal.offer(new Paciente(new PacienteCadastroDTO("P4", LocalDate.now().minusYears(2), "00000000001")));
        filaNormal.offer(new Paciente(new PacienteCadastroDTO("P5", LocalDate.now().minusYears(2), "00000000001")));

        Queue<Paciente> filaPreferencial = new LinkedList<>();
        filaPreferencial.offer(new Paciente(new PacienteCadastroDTO("P6", LocalDate.now().minusYears(2), "00000000001")));
        filaPreferencial.offer(new Paciente(new PacienteCadastroDTO("P7", LocalDate.now().minusYears(2), "00000000001")));
        filaPreferencial.offer(new Paciente(new PacienteCadastroDTO("P8", LocalDate.now().minusYears(2), "00000000001")));

        when(repository.getFilaUrgentePaciente()).thenReturn(filaUrgente);
        when(repository.getFilaNormalPaciente()).thenReturn(filaNormal);
        when(repository.getFilaPreferencialPaciente()).thenReturn(filaPreferencial);

        Queue<Paciente> filaFinal = atendimentoService.listarFilaAtendimento();

        // Verifique se a ordem dos pacientes na fila final está correta
        assertEquals("P1", filaFinal.poll().getNome()); // Urgente
        assertEquals("P2", filaFinal.poll().getNome()); // Urgente
        assertEquals("P6", filaFinal.poll().getNome()); // Preferencial
        assertEquals("P3", filaFinal.poll().getNome()); // Urgente
        assertEquals("P4", filaFinal.poll().getNome()); // Normal
        assertEquals("P7", filaFinal.poll().getNome()); // Preferencial
        assertEquals("P5", filaFinal.poll().getNome()); // Normal
        assertEquals("P8", filaFinal.poll().getNome()); // Preferencial
    }
}