package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario {

    private boolean acessoIrrestrito;

    public Administrador(Long id, String nome, String email, String senha, Cpf cpf, String telefone) {
        super(id, nome, email, senha, cpf, telefone);
    }

    @Override
    public String apresentar() {
        return "Eu sou " + this.getNome() + " administrador e tenho acesso " + (this.acessoIrrestrito ? "Irrestrito" : "Nenhum!");
    }

}
