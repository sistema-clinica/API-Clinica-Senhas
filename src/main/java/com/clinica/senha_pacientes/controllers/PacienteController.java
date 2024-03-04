package com.clinica.senha_pacientes.controllers;

import com.clinica.senha_pacientes.DTOs.ListagemPacienteDTO;
import com.clinica.senha_pacientes.DTOs.PacienteCadastroDTO;
import com.clinica.senha_pacientes.DTOs.RetornoCadastroPacienteDTO;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.services.AtendimentoService;
import com.clinica.senha_pacientes.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @Operation(summary = "Listar todos os pacientes da base de dados")
    public ResponseEntity<List<ListagemPacienteDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @PostMapping("/cadastro")
    @Transactional
    @Operation(summary = "Cadastrar novo paciente",
            description = "Cadastro de um paciente, ele automaticamente é adicionado à lista de espera da triagem.")
    public ResponseEntity<RetornoCadastroPacienteDTO> cadastrarPaciente(@RequestBody @Valid PacienteCadastroDTO pacienteCadastroDTO, UriComponentsBuilder uriBuilder) {
        Paciente pacienteSalvo = pacienteService.salvarPaciente(new Paciente(pacienteCadastroDTO));
        atendimentoService.addPacienteFilaEspera(pacienteSalvo);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(pacienteSalvo.getId()).toUri();

        return ResponseEntity.created(uri).body(new RetornoCadastroPacienteDTO(pacienteSalvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recebe detalhes do paciente pelo seu id")
    public ResponseEntity<ListagemPacienteDTO> detalharPacienteById(@PathVariable Long id) {
        ListagemPacienteDTO paciente = new ListagemPacienteDTO(pacienteService.findPacienteById(id));
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar paciente pelo seu id")
    public ResponseEntity deletarPacienteById(@PathVariable Long id) {
        pacienteService.deletePacienteById(id);

        return ResponseEntity.noContent().build();
    }



}
