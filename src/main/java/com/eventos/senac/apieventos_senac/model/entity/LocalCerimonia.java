package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cnpj;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_localCerimonia")
@SequenceGenerator(name = "seq_localCerimonia", sequenceName = "seq_localCerimonia", allocationSize = 1, initialValue = 1)
public class LocalCerimonia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_localCerimonia")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    @Column(nullable = false)
    private Cnpj cnpj;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private int capacidade;

    private EnumStatusLocalCerimonia status = EnumStatusLocalCerimonia.ATIVO;

    public LocalCerimonia() {
    }


    public LocalCerimonia(String nome, String endereco, int capacidade) {
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

}
