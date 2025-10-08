package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.interfaces.INotificacao;

public class NotificacaoSMS implements INotificacao {

    private final String telefoneDestino;

    public NotificacaoSMS(String numeroDestino) {
        this.telefoneDestino = numeroDestino;
    }

    @Override
    public void Enviar(String mensagem) {
        System.out.println("Telefone Destino: " + this.telefoneDestino + " MENSAGEM: " + mensagem);
    }

}
