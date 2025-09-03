package com.eventos.senac.apieventos_senac.model.valueobjects;

import lombok.Data;

@Data
public class Cpf {

    private final String cpf;

    public Cpf() {
        cpf = "";
    }

    public Cpf(String cpf, Long id) {
        this.cpf = cpf;
    }

    public Cpf(String cpf) {
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return cpf;
    }

}
