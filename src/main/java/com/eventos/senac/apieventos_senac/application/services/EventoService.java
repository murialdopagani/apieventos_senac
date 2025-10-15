package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.domain.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.domain.entity.EventoShow;
import com.eventos.senac.apieventos_senac.domain.entity.EventoWorkshop;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumTipoEvento;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LocalCerimoniaService localCerimoniaService;


    public List<EventoResponseDto> listarEventos() {
        return eventoRepository.findAllByStatusNotOrderById(EnumStatusEvento.EXCLUIDO)
            .stream()
            .map(EventoResponseDto::new)
            .collect(Collectors.toList());
    }

    public EventoResponseDto buscarEventoPorId(Long id) {
        return eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .stream()
            .map(EventoResponseDto::new)
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
    }

    public EventoResponseDto criarEvento(EventoRequestDto eventoDto) {
        Usuario organizador = usuarioService.buscarPorIdObj(eventoDto.organizadorId());
        LocalCerimonia localCerimonia = localCerimoniaService.buscarPorIdObj(eventoDto.localCerimoniaId());
        Evento evento = criarEventoBaseadoNoTipo(eventoDto, organizador, localCerimonia);

        validarDataEvento(evento.getData());
        chekEventoNoBanco(evento);
        EventoResponseDto eventoResponseDto = new EventoResponseDto(eventoRepository.save(evento));
        return eventoResponseDto;
    }

    public EventoResponseDto atualizarEvento(Long id, EventoRequestDto eventoDto) {
        Evento eventoDB = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));

        Usuario organizador = usuarioService.buscarPorIdObj(eventoDto.organizadorId());
        LocalCerimonia localCerimonia = localCerimoniaService.buscarPorIdObj(eventoDto.localCerimoniaId());
        Evento evento = criarEventoBaseadoNoTipo(eventoDto, organizador, localCerimonia);

        validarDataEvento(evento.getData());
        eventoDB = atualizarEventoBaseadoNoTipo(evento, eventoDB);

        EventoResponseDto eventoResponseDto = new EventoResponseDto(eventoRepository.save(eventoDB));
        return eventoResponseDto;
    }

    private Evento criarEventoBaseadoNoTipo(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia);
            case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia);
            case WORKSHOP -> new EventoWorkshop(dto, organizador, localCerimonia);
            case SHOW -> new EventoShow(dto, organizador, localCerimonia);
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
        };
    }

    private Evento atualizarEventoBaseadoNoTipo(Evento eventoDto, Evento eventoDB) {
        if (eventoDto instanceof EventoFormatura eventoFormatura && eventoDB instanceof EventoFormatura eventoBancoFormatura) {
            return eventoBancoFormatura.atualizarEventoFromDTO(eventoBancoFormatura, eventoFormatura);
        } else if (eventoDto instanceof EventoPalestra eventoPalestra && eventoDB instanceof EventoPalestra eventoBancoPalestra) {
            return eventoBancoPalestra.atualizarEventoFromDTO(eventoBancoPalestra, eventoPalestra);
        } else if (eventoDto instanceof EventoShow eventoShow && eventoDB instanceof EventoShow eventoBancoShow) {
            return eventoBancoShow.atualizarEventoFromDTO(eventoBancoShow, eventoShow);
        } else if (eventoDto instanceof EventoWorkshop eventoWorkshop && eventoDB instanceof EventoWorkshop eventoBancoWorkshop) {
            return eventoBancoWorkshop.atualizarEventoFromDTO(eventoBancoWorkshop, eventoWorkshop);
        } else {
            throw new IllegalArgumentException("Tipo de evento inválido: " + eventoDto.getClass().getSimpleName());
        }
    }

    private Evento incrementarInscritos(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.isFull()) {
            throw new ValidacoesRegraNegocioException(
                "Este Evento já está com sua capacidade máxima.!!!");
        }

        if (evento.isPast()) {
            throw new ValidacoesRegraNegocioException("Não é possível se inscrever. Evento já realizado");
        }
        evento.setInscritos(evento.getInscritos() + 1);
        return eventoRepository.save(evento);
    }

    private boolean validarDataEvento(LocalDateTime data) {
        LocalDateTime dataEvento = data;
        LocalDateTime hoje = LocalDate.now().atStartOfDay();

        if (dataEvento.isBefore(hoje)) {
            throw new ValidacoesRegraNegocioException("Não é possível criar evento com data retroativa");
        }
        return true;
    }

    private boolean chekEventoNoBanco(Evento evento) {
        var dataIni = evento.getData();
        var dataFim = dataIni.plusMinutes(evento.getDuracaoMinutos() + 30); // Adiciona 30 minutos de margem
        var dataEvento = dataIni.toLocalDate().atStartOfDay();

        List<Evento> eventoBanco = eventoRepository.findByDataBetweenAndLocalCerimoniaAndStatusNotOrderById(
            dataEvento, dataFim, evento.getLocalCerimonia(), EnumStatusEvento.EXCLUIDO);

        for (Evento eventos : eventoBanco) {
            LocalDateTime dataInicioBanco = eventos.getData();
            LocalDateTime dataFimBanco = dataInicioBanco.plusMinutes(eventos.getDuracaoMinutos() + 30);

            // Verifica se há sobreposição de intervalos
            if ((dataIni.isBefore(dataFimBanco) && dataFim.isAfter(dataInicioBanco))) {
                throw new ValidacoesRegraNegocioException(
                    "Este local de Cerimônia já possui um evento agendado nesta data e horario..!!");
            }
        }
        return true;
    }

    public boolean excluirEvento(Long id) {
        var evento = eventoRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
        alterarStatusEvento(evento, EnumStatusEvento.CANCELADO);
        eventoRepository.save(evento);
        return true;
    }

    public boolean ativar(Long id) {
        var evento = eventoRepository.findByIdAndStatus(id, EnumStatusEvento.CANCELADO).orElseThrow(() ->
            new RegistroNaoEncontradoException("Evento não encontrado"));
        alterarStatusEvento(evento, EnumStatusEvento.ATIVO);
        eventoRepository.save(evento);
        return true;
    }

    private void alterarStatusEvento(Evento eventoDB, EnumStatusEvento statusEvento) {
        eventoDB.setStatus(statusEvento);
        eventoRepository.save(eventoDB);
    }

}
