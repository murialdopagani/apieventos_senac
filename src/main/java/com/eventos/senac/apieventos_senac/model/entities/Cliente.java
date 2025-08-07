package com.eventos.senac.apieventos_senac.model.entities;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cliente extends Usuario {
    private BigDecimal valorConsumido;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email, String senha, Cpf cpf, BigDecimal valorConsumido) {
        super(id, nome, email, senha, cpf);
        this.setValorConsumido(valorConsumido);
    }

    public BigDecimal getValorConsumido() {
        return valorConsumido;
    }

    public void setValorConsumido(BigDecimal valorConsumido) {
        this.valorConsumido = valorConsumido;
    }

    @Override
    public String apresentar() {
        String respota = "";
        respota += super.apresentar() + "Valor Consumido: " + this.valorConsumido.toString();
        return respota;
    }
}
