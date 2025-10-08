package com.eventos.senac.apieventos_senac.domain.valueobjects;

public enum EnumTipoEvento {
    FORMATURA("Formatura",1),
    PALESTRA("Palestra", 2),
    SHOW("Show", 3),
    WORKSHOP("Workshop",4);

    private final String descricao;
    private final int codigo;

    // Construtor do enum (privado por padrão)
    EnumTipoEvento(String descricao, int codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    // Métodos getter para acessar os atributos
    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    // Método estático opcional para obter um enum pela descrição
    public static EnumTipoEvento fromString(String tipo) {
        for (EnumTipoEvento tipoEvento : EnumTipoEvento.values()) {
            if (tipoEvento.name().equalsIgnoreCase(tipo)) {
                return tipoEvento;
            }
        }
        throw new IllegalArgumentException("Tipo de evento inválido: " + tipo);
    }

    // Método estático opcional para obter um enum pelo código
    public static EnumTipoEvento fromCodigo(int codigo) {
        for (EnumTipoEvento tipoEvento : EnumTipoEvento.values()) {
            if (tipoEvento.getCodigo() == codigo) {
                return tipoEvento;
            }
        }
        throw new IllegalArgumentException("Código de evento inválido: " + codigo);
    }
}
