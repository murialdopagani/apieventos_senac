package com.eventos.senac.apieventos_senac.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoUsuario {
    ADMIN("ADMIN",0),
    USUARIO("USUARIO",1),
    PARTICIPANTE("PARTICIPANTE",2);

    private final String descricao;
    private final int codigo;

    EnumTipoUsuario(String descricao, int codigo) {
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
    public static EnumTipoUsuario fromCodigo(int codigo) {
        for (EnumTipoUsuario tipo : EnumTipoUsuario.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de usuário inválido: " + codigo);
    }


    // Método estático opcional para obter um enum pela descrição
    public static EnumTipoUsuario fromString(String descricao) {
        for (EnumTipoUsuario tipoUsuario : EnumTipoUsuario.values()) {
            if (tipoUsuario.name().equalsIgnoreCase(descricao)) {
                return tipoUsuario;
            }
        }
        throw new IllegalArgumentException("Tipo de usuário inválido: " + descricao);
    }


}
