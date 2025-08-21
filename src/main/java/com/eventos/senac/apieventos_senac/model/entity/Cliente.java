package com.eventos.senac.apieventos_senac.model.entity;

import java.math.BigDecimal;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario {

    private BigDecimal valorConsumido;

    public Cliente() {
    }

    public Cliente(Long id,
                   String nome,
                   String email,
                   String senha,
                   Cpf cpf,
                   BigDecimal valorConsumido,
                   String telefone) {
        super(id, nome, email, senha, cpf, telefone);
        this.setValorConsumido(valorConsumido);
    }

    @Override
    public String apresentar() {
        String respota = "";
        respota += super.apresentar() + "Valor Consumido: " + this.valorConsumido.toString();
        return respota;
    }

}
