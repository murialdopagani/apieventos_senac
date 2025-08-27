package com.eventos.senac.apieventos_senac.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class Cpf {


    private final String cpf;

    public Cpf(String cpf) {
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return cpf;
    }


}
