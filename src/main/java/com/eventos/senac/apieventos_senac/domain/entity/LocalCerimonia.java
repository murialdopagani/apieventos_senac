package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.CNPJ;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_local_cerimonia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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


}
