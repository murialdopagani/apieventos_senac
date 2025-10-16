package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.valueobjects.CPF;

//@Data
//@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario {

    private boolean acessoIrrestrito;

    public Administrador(Long id, String nome, String email, String senha, CPF cpf, String telefone) {
        super(id, nome, email, senha, cpf, telefone);
    }

    @Override
    public String apresentar() {
        return "Eu sou " + this.getNome() + " administrador e tenho acesso " + (this.acessoIrrestrito ? "Irrestrito" : "Nenhum!");
    }


    public boolean isAcessoIrrestrito() {
        return acessoIrrestrito;
    }

    public void setAcessoIrrestrito(boolean acessoIrrestrito) {
        this.acessoIrrestrito = acessoIrrestrito;
    }


}
