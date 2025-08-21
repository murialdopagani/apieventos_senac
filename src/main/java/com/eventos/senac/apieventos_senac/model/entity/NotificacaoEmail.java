package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.model.interfaces.INotificacao;

public class NotificacaoEmail implements INotificacao {

    private String emailDestino;


    public NotificacaoEmail(String emailDestino) {
        this.emailDestino = emailDestino;

    }

    @Override
    public void Enviar(String mensagem) {
        System.out.println("Email para : " + this.emailDestino + " MESAGEM: " + mensagem);

    }

}
