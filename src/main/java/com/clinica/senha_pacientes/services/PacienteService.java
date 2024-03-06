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

    public Paciente salvarPaciente(Paciente paciente) {
        paciente.setSenha(gerarSenhaUnica());
        return pacienteRepository.save(paciente);
    }

    private String gerarSenhaUnica() {
        String senhaStr = "00001";
        String ultimaSenha = findUltimaSenhaCriada();
        if (ultimaSenha != null) {
            int valorUltimaSenha = Integer.valueOf(ultimaSenha);
            senhaStr = String.format("%05d", valorUltimaSenha + 1);
        }
        return senhaStr;
    }

    public List<ListagemPacienteDTO> listarPacientes() {
        return pacienteRepository.findAll().stream().map(ListagemPacienteDTO::new).toList();
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

    private String findUltimaSenhaCriada() {
        return pacienteRepository.buscarUltimaSenhaCriada();
    }

    public List<ListagemPacienteDTO> listarPacientesEmTriagem() {
        List<Paciente> pacientes = pacienteRepository.findPacientesByStatus(Status.EM_TRIAGEM);
        return pacientes.stream().map(ListagemPacienteDTO::new).toList();
    }
}
