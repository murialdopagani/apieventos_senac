package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
public class Administrador extends Usuario {

    @Column
    private boolean acessoIrrestrito;

    public Administrador(UsuarioCriarRequestDto usuarioRequest) {
        super(usuarioRequest);
        this.setAcessoIrrestrito(true);
    }

    @Override
    public String apresentar() {
        return " Dados do Administratod Nome: " + this.getNome() +
            " CPF Format " + this.getCpf().toString() + " Nivel de acesso Irrestrito: " + this.isAcessoIrrestrito();
    }

    public boolean isAcessoIrrestrito() {
        return acessoIrrestrito;
    }

    public void setAcessoIrrestrito(boolean acessoIrrestrito) {
        this.acessoIrrestrito = acessoIrrestrito;
    }
}
