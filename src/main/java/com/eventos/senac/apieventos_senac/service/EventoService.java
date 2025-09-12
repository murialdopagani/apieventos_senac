package com.eventos.senac.apieventos_senac.service;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumTipoEvento;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento criarEvento(EventoCriarRequestDto eventoCriarRequestDto) {
        Usuario organizador = usuarioRepository.findById(eventoCriarRequestDto.organizadorId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Evento evento = criarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador);

        return eventoRepository.save(evento);
    }

    // Metodo Factory para criar a instância do evento
    private Evento criarEventoBaseadoNoTipo(EventoCriarRequestDto dto, Usuario organizador) {

        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA -> new EventoFormatura(dto, organizador);
            //case PALESTRA -> new EventoPalestra(dto, organizador);
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
        };
    }



    public List<Evento> listarTodosEventos() {
        return eventoRepository.findAllByOrderByDataAsc();
    }



    public Evento incrementarInscritos(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.isFull()) {
            throw new RuntimeException("Evento lotado");
        }

        if (evento.isPast()) {
            throw new RuntimeException("Não é possível se inscrever em evento já realizado");
        }
        evento.setInscritos(evento.getInscritos() + 1);
        return eventoRepository.save(evento);
    }

}
