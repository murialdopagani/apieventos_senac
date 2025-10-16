package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.CNPJ;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_local_cerimonia")
public class LocalCerimonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private CNPJ cnpj;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private int capacidade;

    @Column
    private EnumStatusLocalCerimonia status = EnumStatusLocalCerimonia.ATIVO;

    public LocalCerimonia() {
    }

    public LocalCerimonia(LocalCerimoniaCriarRequestDto localCerimonaDto) {
        this.nome = localCerimonaDto.nome();
        this.cnpj = new CNPJ(localCerimonaDto.cnpj());
        this.endereco = localCerimonaDto.endereco();
        this.capacidade = localCerimonaDto.capacidade();
    }

    public LocalCerimonia atualizarLocalCerimoniaFromDto(LocalCerimonia localCerimoniaBanco, LocalCerimoniaCriarRequestDto dto) {
        localCerimoniaBanco.setNome(dto.nome());
        localCerimoniaBanco.setCnpj(new CNPJ(dto.cnpj()));
        localCerimoniaBanco.setEndereco(dto.endereco());
        localCerimoniaBanco.setCapacidade(dto.capacidade());
        return localCerimoniaBanco;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CNPJ getCnpj() {
        return cnpj;
    }

    public void setCnpj(CNPJ cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public EnumStatusLocalCerimonia getStatus() {
        return status;
    }

    public void setStatus(EnumStatusLocalCerimonia status) {
        this.status = status;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
