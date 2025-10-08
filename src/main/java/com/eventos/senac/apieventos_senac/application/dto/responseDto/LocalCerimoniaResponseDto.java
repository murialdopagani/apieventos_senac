package com.eventos.senac.apieventos_senac.application.dto.responseDto;

import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;

public record LocalCerimoniaResponseDto(Long id, String nome, String cnpj, String endereco, int capacidade) {

    // Metodo estático factory
    public static LocalCerimoniaResponseDto fromLocalCerimonia(LocalCerimonia localCerimonia){
        return new LocalCerimoniaResponseDto(localCerimonia.getId(), localCerimonia.getNome(),
                localCerimonia.getCnpj() != null ? localCerimonia.getCnpj().toString() : null,
                localCerimonia.getEndereco(), localCerimonia.getCapacidade());
    }

}
