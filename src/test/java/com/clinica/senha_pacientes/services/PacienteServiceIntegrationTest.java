package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.DTOs.PacienteCadastroDTO;
import com.clinica.senha_pacientes.enitites.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PacienteServiceIntegrationTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @DisplayName("Verificar se a senha está sendo gerada de forma correta e não existem duplicações")
    void salvarPacienteTest() {
        var paciente1 = new Paciente(new PacienteCadastroDTO("João Arthur", LocalDate.of(2006, Month.JANUARY, 20), "00000000001"));
        var paciente2 = new Paciente(new PacienteCadastroDTO("Lucas Souza", LocalDate.of(2003, Month.FEBRUARY, 06), "00000000002"));

        var pacienteSalvo1 = pacienteService.salvarPaciente(paciente1);
        var pacienteSalvo2 = pacienteService.salvarPaciente(paciente2);
        assertEquals("00001", pacienteSalvo1.getSenha());
        assertEquals("00002", pacienteSalvo2.getSenha());

        for (int i = 3; i < 10000; i++) {
            var pacienteSalvo = salvarPaciente();
        }
        var pacienteSalvo100000 = salvarPaciente();
        assertEquals("10000", pacienteSalvo100000.getSenha());
    }

    private Paciente salvarPaciente() {
        Paciente paciente = new Paciente(new PacienteCadastroDTO("Paciente Teste", LocalDate.now().minusYears(2), "00000000003"));
        return pacienteService.salvarPaciente(paciente);
    }

}