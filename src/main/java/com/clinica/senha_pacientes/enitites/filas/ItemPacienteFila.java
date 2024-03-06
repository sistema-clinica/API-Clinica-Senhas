package com.clinica.senha_pacientes.enitites.filas;

import com.clinica.senha_pacientes.enitites.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class ItemPacienteFila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @Column(name = "data_de_adicao")
    @JoinColumn(name = "paciente_id")
    private LocalDateTime dataDeAdicao;

    public ItemPacienteFila(Paciente paciente) {
        this.paciente = paciente;
        this.dataDeAdicao = LocalDateTime.now();
    }
}
