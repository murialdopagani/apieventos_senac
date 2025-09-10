package com.eventos.senac.apieventos_senac.model.valueobjects;

public record Cpf(String cpf) {

    public Cpf() {
        this("");
    }

    //    public Cpf(String cpf, Long id) {
    //        this.cpf = cpf;
    //    }

    public Cpf(String cpf) {
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return cpf;
    }

}
