package com.clinica.senha_pacientes.controllers;

import com.clinica.senha_pacientes.DTOs.ChamadaDTO;
import com.clinica.senha_pacientes.DTOs.ListagemPacienteDTO;
import com.clinica.senha_pacientes.DTOs.LocalChamadaDTO;
import com.clinica.senha_pacientes.DTOs.PacienteTriagemDTO;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Urgencia;
import com.clinica.senha_pacientes.services.AtendimentoService;
import com.clinica.senha_pacientes.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("atendimento")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @MessageMapping("/atendimento")
    @SendTo("/painel")
    @Operation(summary = "Chamar paciente para atendimento",
            description = "Chama o paciente no topo da lista de atendimento e o coloca como status de finalizado atendimento.")
    public ResponseEntity<ChamadaDTO> getPacienteAtendimento(@RequestBody @Valid LocalChamadaDTO localChamadaDTO) {
        Paciente paciente = atendimentoService.dequeuePaciente();
        paciente.setLocalAtendiemntoDTO(localChamadaDTO);
        pacienteService.atualizarPaciente(paciente);

        return ResponseEntity.ok(new ChamadaDTO(paciente));
    }

    @PostMapping("/espera")
    @MessageMapping("/espera")
    @SendTo("/painel")
    @Operation(summary = "Chamar paciente para triagem",
            description = "Chama o paciente no topo da fila de triagem, é necessário enviar a sala de triagem que ele será atendido")
    public ResponseEntity<ChamadaDTO> getPacienteTriagem(@RequestBody @Valid LocalChamadaDTO localChamadaDTO){
        Paciente paciente = atendimentoService.dequeuePacienteEspera();
        paciente.setLocalAtendiemntoDTO(localChamadaDTO);
        pacienteService.atualizarPaciente(paciente);
        return ResponseEntity.ok(new ChamadaDTO(paciente));
    }

    @GetMapping("/fila")
    @Operation(summary = "Listagem de todos os paciente na fila de atendimento")
    public ResponseEntity<List<ListagemPacienteDTO>> listarPacientesAtendimento() {
        List<ListagemPacienteDTO> list = atendimentoService.listarFilaAtendimento().stream().map(ListagemPacienteDTO::new).toList();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fila/espera")
    @Operation(summary = "Listagem de todos os paciente na fila de triagem")
    public ResponseEntity<List<ListagemPacienteDTO>> listarPacientesEspera() {
        List<ListagemPacienteDTO> list = atendimentoService.listarFilaEspera().stream().map(ListagemPacienteDTO::new).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/triagem")
    @Transactional
    @Operation(summary = "Registrar triagem do paciente",
            description = "Registra os dados da triagem do paciente e o adiciona à fila de atendimento.")
    public ResponseEntity setTriagemPaciente(@RequestBody @Valid PacienteTriagemDTO pacienteTriagemDTO) {
        Paciente paciente = pacienteService.findPacienteBySenha(pacienteTriagemDTO.senha());
        paciente.setTriagem(pacienteTriagemDTO);

        atendimentoService.addPacienteFila(paciente);
        return ResponseEntity.ok("Triagem realizada com sucesso!");
    }

    @GetMapping("/triagem")
    @Operation(summary = "Lista todos os pacientes que estão em triagem")
    public ResponseEntity<List<ListagemPacienteDTO>> listagemPacientesEmTriagem() {
        var pacientes = pacienteService.listarPacientesEmTriagem();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/ultimo")
    @Operation(summary = "Chamar novamente último paciente chamado")
    public ResponseEntity<ChamadaDTO> chamarPacienteNovamente() {
        var ultimoPaciente = new ChamadaDTO(atendimentoService.getUltimaSenhaChamada());
        return ResponseEntity.ok(ultimoPaciente);
    }

    @GetMapping("/recentes")
    @Operation(summary = "Listagem dos últimos paciente chamados")
    public ResponseEntity<List<ChamadaDTO>> BuscarPacientesRecentes() {
        var pacientesRecentes = atendimentoService.getHistoricoSenhasChamadas().stream().map(ChamadaDTO::new).toList();
        return ResponseEntity.ok(pacientesRecentes);
    }

    @GetMapping("/{urgencia}")
    @Operation(summary = "Listagem de paciente na lista de atendimento filtrada pela sua urgência")
    public ResponseEntity<List<ListagemPacienteDTO>> BuscarPacientesPorUrgencia(@PathVariable Urgencia urgencia) {
        var pacientesUrgencia =  atendimentoService.getPacientesByUrgencia(urgencia).stream().map(ListagemPacienteDTO::new).toList();
        return ResponseEntity.ok(pacientesUrgencia);
    }
}
