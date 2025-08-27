package com.eventos.senac.apieventos_senac.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cpf")
@SequenceGenerator(name = "seq_cpf", sequenceName = "seq_cpf", allocationSize = 1, initialValue = 1)
public class Cpf {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cpf")
    private Long id;

    @Column(nullable = false, unique = true)
    private final String cpf;

    public Cpf() {
        cpf = "";
    }

    public Cpf(String cpf, Long id) {
        this.cpf = cpf;
        this.id = id;
    }

    public Cpf(String cpf) {
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return cpf;
    }

}
