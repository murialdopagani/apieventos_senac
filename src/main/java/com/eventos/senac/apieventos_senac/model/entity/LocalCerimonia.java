package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.requestDto.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cnpj;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
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

    public LocalCerimonia(LocalCerimoniaCriarRequestDto localCerimonaDto) {
        this.nome = localCerimonaDto.nome();
        this.cnpj = new Cnpj(localCerimonaDto.cnpj());
        this.endereco = localCerimonaDto.endereco();
        this.capacidade = localCerimonaDto.capacidade();
    }

    public LocalCerimonia atualizarLocalCerimoniaFromDto(LocalCerimonia localCerimoniaBanco, LocalCerimoniaCriarRequestDto dto) {
        localCerimoniaBanco.setNome(dto.nome());
        localCerimoniaBanco.setCnpj(new Cnpj(dto.cnpj()));
        localCerimoniaBanco.setEndereco(dto.endereco());
        localCerimoniaBanco.setCapacidade(dto.capacidade());
        return localCerimoniaBanco;

    }

    public int getCapacidade() {
        return capacidade;
    }

}
