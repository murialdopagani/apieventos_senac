package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.interfaces.INotificacao;

public class NotificacaoEmail implements INotificacao {

    private final String emailDestino;

    public NotificacaoEmail(String emailDestino) {
        this.emailDestino = emailDestino;

    }

    @Override
    public void Enviar(String mensagem) {
        System.out.println("Email para : " + this.emailDestino + " MESAGEM: " + mensagem);

    }

}
