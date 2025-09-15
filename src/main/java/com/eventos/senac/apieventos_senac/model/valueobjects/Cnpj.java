package com.eventos.senac.apieventos_senac.model.valueobjects;

public record Cnpj(String cnpj) {

    public Cnpj() {
        this("");
    }

    public Cnpj(String cnpj) {
        this.cnpj = cnpj.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return cnpj;
    }
}
