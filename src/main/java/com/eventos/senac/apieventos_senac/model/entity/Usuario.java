package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import lombok.Data;

@Data
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Cpf cpf;

    public Usuario() {
    }


    public Usuario(Long id, String nome, String email, String senha, Cpf cpf) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
    }


    public String apresentar() {
        return  "Dados " + this.nome +
                " Cpf Format : " + this.cpf.toString();
    }

}
