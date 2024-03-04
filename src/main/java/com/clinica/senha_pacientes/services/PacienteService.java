package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.DTOs.ListagemPacienteDTO;
import com.clinica.senha_pacientes.enitites.Paciente;
import com.clinica.senha_pacientes.enitites.Status;
import com.clinica.senha_pacientes.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private int ultimoNumeroSenha = 0;

    public Paciente salvarPaciente(Paciente paciente) {
        paciente.setSenha(gerarSenhaUnica());
        return pacienteRepository.save(paciente);
    }

    public List<ListagemPacienteDTO> listarPacientes() {
        return pacienteRepository.findAll().stream().map(ListagemPacienteDTO::new).toList();
    }

    private String gerarSenhaUnica() {
        ultimoNumeroSenha++;
        String senhaStr = String.format("%05d", ultimoNumeroSenha);
        return senhaStr;
    }

    public Paciente atualizarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente findPacienteBySenha(String senha) {
        return pacienteRepository.findPacienteBySenha(senha);
    }

    public Paciente findPacienteById(Long id) {
        return pacienteRepository.getReferenceById(id);
    }

    public void deletePacienteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    public List<ListagemPacienteDTO> listarPacientesEmTriagem() {
        var pacientes = pacienteRepository.findPacientesByStatus(Status.EM_TRIAGEM);
        return pacientes.stream().map(ListagemPacienteDTO::new).toList();
    }
}
