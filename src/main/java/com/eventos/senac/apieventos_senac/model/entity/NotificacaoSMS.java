package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.model.interfaces.INotificacao;

public class NotificacaoSMS implements INotificacao {

    private String telefoneDestino;

    public NotificacaoSMS(String numeroDestino) {
        this.telefoneDestino = numeroDestino;
    }

    @Override
    public void Enviar(String mensagem) {
        System.out.println("Telefone Destino: " + this.telefoneDestino + " MENSAGEM: " + mensagem);
    }

}
