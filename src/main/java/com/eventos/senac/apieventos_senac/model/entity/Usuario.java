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

  private String telefone;

  public Usuario() {}


  public Usuario(Long id, String nome, String email, String senha, Cpf cpf, String telefone) {
    this.setId(id);
    this.setNome(nome);
    this.setCpf(cpf);
    this.setEmail(email);
    this.setSenha(senha);
    this.setTelefone(telefone);
  }


  public String apresentar() {
    return "Dados " + this.nome + " Cpf Format : " + this.cpf.toString();
  }

}
