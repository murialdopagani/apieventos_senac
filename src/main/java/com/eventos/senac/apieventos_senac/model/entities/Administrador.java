package com.eventos.senac.apieventos_senac.model.entities;

public class Administrador extends Usuario{
    private boolean AcessoIrrestrito;

    public boolean isAcessoIrrestrito() {
        return AcessoIrrestrito;
    }

    public void setAcessoIrrestrito(boolean acessoIrrestrito) {
        AcessoIrrestrito = acessoIrrestrito;
    }
}
