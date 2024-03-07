package com.clinica.senha_pacientes.enitites;

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
@Entity
@Table(name = "item_paciente_fila")
public class ItemPacienteFila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_fila")
    private TipoFila tipoFila;

    @Column(name = "data_de_adicao")
    private LocalDateTime dataDeAdicao;

    public ItemPacienteFila(Paciente paciente, TipoFila tipoFila) {
        this.paciente = paciente;
        this.tipoFila = tipoFila;
        this.dataDeAdicao = LocalDateTime.now();
    }

    public ItemPacienteFila(Paciente paciente) {
        this.paciente = paciente;
        this.dataDeAdicao = LocalDateTime.now();
    }
}
