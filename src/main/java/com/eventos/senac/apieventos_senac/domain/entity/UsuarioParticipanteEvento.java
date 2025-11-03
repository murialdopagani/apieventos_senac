package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("PARTICIPANTE")
@NoArgsConstructor
public class UsuarioParticipanteEvento extends Usuario{

    public UsuarioParticipanteEvento(UsuarioCriarRequestDto usuarioRequest) {
        super(usuarioRequest);
        this.setAcessoIrrestrito(false);
    }

}
