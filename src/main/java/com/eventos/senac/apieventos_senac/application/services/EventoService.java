package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.domain.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.domain.entity.EventoShow;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.domain.repository.UsuarioRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalCerimoniaRepository localCerimoniaRepository;

    public List<EventoResponseDto> listarEventos() {
        return eventoRepository.findAllByStatusNotOrderById(EnumStatusEvento.EXCLUIDO)
            .stream()
            .map(EventoResponseDto::fromEvento)
            .collect(Collectors.toList());
    }

    public EventoResponseDto buscarEventoPorId(Long id) {
        return eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .stream()
            .map(EventoResponseDto::fromEvento)
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
    }

    public EventoResponseDto deletarEventoPorId(Long id) {
        Evento evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.EXCLUIDO);
        eventoRepository.save(evento);
        return EventoResponseDto.fromEvento(evento);
    }

    public Evento criarEvento(Evento evento) {
        validarDataEvento(evento.getData());
        chekEventoNoBanco(evento);
        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(Long id, Evento evento) {

        var eventoBanco = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));

        validarDataEvento(evento.getData());
        eventoBanco = atualizarEventoBaseadoNoTipo(evento, eventoBanco);
        return eventoRepository.save(eventoBanco);
    }

//    public Evento atualizarEvento(Long id, EventoRequestDto eventoRequestDto)  {
//
//        var eventoBanco = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
//            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
//
//        validarDataEvento(eventoRequestDto.data());
//
//        Usuario organizador = buscarUsuarioNoBanco(eventoRequestDto.organizadorId()).orElseThrow(
//            () -> new RegistroNaoEncontradoException("Usuário não encontrado.!!"));
//
//        LocalCerimonia localCerimonia = buscarLocalCerimoniaNoBanco(eventoRequestDto.localCerimoniaId()).orElseThrow(
//            () -> new RegistroNaoEncontradoException("Local de cerimônia não encontrado"));
//
//        eventoBanco = atualizarEventoBaseadoNoTipo(eventoRequestDto, organizador, localCerimonia, eventoBanco);
//
//        return eventoRepository.save(eventoBanco);
//    }

    public EventoResponseDto cancelar(Long id) {
        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElseThrow(() ->
            new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.CANCELADO);
        eventoRepository.save(evento);
        return EventoResponseDto.fromEvento(evento);
    }

    public EventoResponseDto ativar(Long id) {
        var evento = eventoRepository.findByIdAndStatus(id, EnumStatusEvento.CANCELADO).orElseThrow(() ->
            new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.ATIVO);
        eventoRepository.save(evento);
        return EventoResponseDto.fromEvento(evento);
    }

    // Metodo Factory para criar a instância do evento
//    private Evento criarEventoBaseadoNoTipo(EventoRequestDto dto, Usuario organizador,
//        LocalCerimonia localCerimonia) {
//
//        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());
//
//        return switch (tipoEvento) {
//            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia);
//            case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia);
//            case SHOW -> new EventoShow(dto, organizador, localCerimonia);
//            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
//        };
//    }

    // Metodo Factory para atualizar um evento
//    private Evento atualizarEventoBaseadoNoTipo(EventoRequestDto dto, Usuario organizador,
//        LocalCerimonia localCerimonia, Evento eventoBanco) {
//        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());
//
//        return switch (tipoEvento) {
//            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia).atualizarEventoFromDTO(
//                (EventoFormatura) eventoBanco, dto, organizador, localCerimonia);
//            case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia).atualizarEventoFromDTO(
//                (EventoPalestra) eventoBanco, dto, organizador, localCerimonia);
//            case SHOW -> new EventoShow(dto, organizador, localCerimonia).atualizarEventoFromDTO(
//                (EventoShow) eventoBanco, dto, organizador, localCerimonia);
//            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
//        };
//    }

    private Evento atualizarEventoBaseadoNoTipo(Evento evento, Evento eventoBanco) {
        if (evento instanceof EventoFormatura eventoFormatura && eventoBanco instanceof EventoFormatura eventoBancoFormatura) {
            return eventoBancoFormatura.atualizarEventoFromDTO(eventoBancoFormatura, eventoFormatura);
        } else if (evento instanceof EventoPalestra eventoPalestra && eventoBanco instanceof EventoPalestra eventoBancoPalestra) {
            return eventoBancoPalestra.atualizarEventoFromDTO(eventoBancoPalestra, eventoPalestra);
        } else {
            throw new IllegalArgumentException("Tipo de evento inválido: " + evento.getClass().getSimpleName());
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

    private void validarDataEvento(LocalDateTime data) {
        LocalDateTime dataEvento = data;
        LocalDateTime hoje = LocalDate.now().atStartOfDay();

        if (dataEvento.isBefore(hoje)) {
            throw new ValidacoesRegraNegocioException("Não é possível criar evento com data retroativa");
        }
    }

    private Optional<Evento> chekEventoNoBanco(Evento evento) {
        var dataIni = evento.getData();
        var dataFim = dataIni.plusMinutes(evento.getDuracaoMinutos() + 30); // Adiciona 30 minutos de margem
        var dataEvento = dataIni.toLocalDate().atStartOfDay();

        List<Evento> eventoBanco = eventoRepository.findByDataBetweenAndLocalCerimoniaAndStatusNotOrderById(
            dataEvento, dataFim, evento.getLocalCerimonia(), EnumStatusEvento.EXCLUIDO);

        for (Evento eventos : eventoBanco) {
            LocalDateTime dataInicioBanco = evento.getData();
            LocalDateTime dataFimBanco = dataInicioBanco.plusMinutes(eventos.getDuracaoMinutos() + 30);

            // Verifica se há sobreposição de intervalos
            if ((dataIni.isBefore(dataFimBanco) && dataFim.isAfter(dataInicioBanco))) {
                throw new ValidacoesRegraNegocioException(
                    "Este local de Cerimônia já possui um evento agendado nesta data e horario..!!");
            }
        }
        return eventoBanco.isEmpty() ? Optional.empty() : Optional.of(eventoBanco.get(0));
    }


    public Optional<Usuario> buscarUsuarioNoBanco(Long id) {
        return usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO);
    }

    public Optional<LocalCerimonia> buscarLocalCerimoniaNoBanco(Long id) {
        return localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO);

    }

}
