package com.clinica.senha_pacientes.enitites;

import com.clinica.senha_pacientes.DTOs.LocalChamadaDTO;
import com.clinica.senha_pacientes.DTOs.PacienteCadastroDTO;
import com.clinica.senha_pacientes.DTOs.PacienteTriagemDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataNasc;
    private String cpf;

    private String senha;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String localDeAtendimento;

    @Enumerated(EnumType.STRING)
    private Urgencia urgencia;

    private String detalheSintomas;

    public void setTriagem(PacienteTriagemDTO pacienteTriagemDTO) {
        if (this.status == Status.EM_TRIAGEM) {
            this.status = Status.PENDENTE_ATENDIMENTO;
            this.urgencia = pacienteTriagemDTO.urgencia();
            this.detalheSintomas = pacienteTriagemDTO.detalheSintomas();
        }
        else {
            throw new RuntimeException("Paciente não está em Triagem");
        }
    }

    public Paciente(PacienteCadastroDTO pacienteCadastroDTO) {
        this.nome = pacienteCadastroDTO.nome();
        this.dataNasc = pacienteCadastroDTO.dataNasc();
        this.cpf = pacienteCadastroDTO.cpf();
        this.status = Status.PENDENTE_TRIAGEM;
    }

    public void foiAtendido() {
        this.status = Status.FINALIZADO_ATENDIMENTO;
    }

    public void atendimentoPendente() {
        this.status = Status.PENDENTE_ATENDIMENTO;
    }

    public void setLocalAtendiemntoDTO(LocalChamadaDTO localChamadaDTO) {
        this.localDeAtendimento = localChamadaDTO.localDeAtendimento();
    }

    public boolean filterUrgencia(Urgencia urgencia) {
        return urgencia == this.urgencia;
    }

    public void emTriagem() {
        this.status = Status.EM_TRIAGEM;
    }
}
