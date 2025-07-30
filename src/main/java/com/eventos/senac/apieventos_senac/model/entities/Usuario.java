package com.eventos.senac.apieventos_senac.model.entities;

import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;

public class Usuario {
    private Long id;
    private String nome;
    private String senha;
    private Cpf cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public String apresentarCpf() {
        return  "Dados " + this.nome +
                "Cpf Format : " + this.cpf.toString();
    }

}
