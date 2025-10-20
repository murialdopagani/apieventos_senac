package com.eventos.senac.apieventos_senac.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoIngresso {
    VIP("Vip",0),
    NORMAL("Normal",1),
    ESTUDANTE("Estudante",2),
    GRATUITO("Gratuito",3);

    private final String descricao;
    private final int codigo;

    EnumTipoIngresso(String descricao, int codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }


    // Anotação para indicar que este método deve ser usado para serializar o enum para JSON.
    // O enum será representado pelo valor do seu código.
    @JsonValue
    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // Anotação para indicar que este método será usado para criar o enum a partir de um valor JSON (neste caso, o int).
    @JsonCreator
    public static EnumTipoIngresso fromCodigo(int codigo) {
        for (EnumTipoIngresso tipo : EnumTipoIngresso.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de ingresso inválido: " + codigo);
    }

    // Método estático opcional para obter um enum pela descrição
    public static EnumTipoIngresso fromString(String descricao) {
        for (EnumTipoIngresso tipoIngresso : EnumTipoIngresso.values()) {
            if (tipoIngresso.name().equalsIgnoreCase(descricao)) {
                return tipoIngresso;
            }
        }
        throw new IllegalArgumentException("Tipo de evento inválido: " + descricao);
    }


}
