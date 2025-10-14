package com.eventos.senac.apieventos_senac.application.dto.localCerimonia;

import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;

public record LocalCerimoniaResponseDto(Long id, String nome, String cnpj, String endereco, int capacidade) {

    public LocalCerimoniaResponseDto(LocalCerimonia localCerimonia) {
        this(localCerimonia.getId(),
            localCerimonia.getNome(),
            localCerimonia.getCnpj().getNumero(),
            localCerimonia.getEndereco(),
            localCerimonia.getCapacidade());
    }


    // Metodo estático factory
    public static LocalCerimoniaResponseDto fromLocalCerimonia(LocalCerimonia localCerimonia){
        return new LocalCerimoniaResponseDto(localCerimonia.getId(), localCerimonia.getNome(),
                localCerimonia.getCnpj() != null ? localCerimonia.getCnpj().toString() : null,
                localCerimonia.getEndereco(), localCerimonia.getCapacidade());
    }

}
