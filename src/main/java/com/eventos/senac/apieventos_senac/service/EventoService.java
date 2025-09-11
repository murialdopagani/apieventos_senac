package com.eventos.senac.apieventos_senac.service;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento criarEvento(EventoCriarRequestDto eventoCriarRequestDto) {
        Usuario organizador = usuarioRepository.findById(eventoCriarRequestDto.organizadorId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var  evento = new EventoFormatura(eventoCriarRequestDto, organizador);

        return eventoRepository.save(evento);
    }
}
