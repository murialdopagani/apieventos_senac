package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoRequestDto;
import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.Inscricao;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.domain.repository.InscricaoRepository;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscricaoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Inscreve um usuário em um evento. Cria a entidade Inscricao mínima (usuario) e delega
     * a validação para o domínio (Evento.inscrever).
     * Retorna o EventoResponseDto atualizado.
     */
    public EventoResponseDto inscrever(InscricaoRequestDto inscricaoRequestDto) {
        Usuario usuario = usuarioService.buscarPorIdObj(inscricaoRequestDto.usuarioId());

        Evento evento = eventoRepository.findById(inscricaoRequestDto.eventoId())
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));

        // checa duplicata no banco
        if (inscricaoRepository.findByEventoIdAndUsuarioId(inscricaoRequestDto.eventoId(), inscricaoRequestDto.usuarioId()).isPresent()) {
            throw new ValidacoesRegraNegocioException("Participante já inscrito neste evento");
        }

        Inscricao inscricao = new Inscricao(inscricaoRequestDto, usuario, evento);

        try {
            evento.inscrever(inscricao); // validações de domínio (lotação, data, duplicatas na coleção)
            inscricaoRepository.save(inscricao);
        } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException ex) {
            // Conflito de concorrência / integridade: repassa como regra de negócio para camada superior
            throw new ValidacoesRegraNegocioException("Não foi possível concluir a inscrição por conflito. Tente novamente.");
        }

        // retorna DTO atualizado
        Evento refreshed = eventoRepository.findById(inscricaoRequestDto.eventoId()).orElse(evento);
        return new EventoResponseDto(refreshed);
    }

    /**
     * Cancela a inscrição de um usuário em um evento (se existir).
     * Retorna true se havia inscrição e foi removida.
     */
    @Transactional
    public boolean cancelarInscricao(Long eventoId, Long usuarioId) {
        var opt = inscricaoRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId);
        var inscricao = opt.orElseThrow(() -> new RegistroNaoEncontradoException("Inscrição não encontrada"));

        Evento evento = inscricao.getEvento();
        if (evento == null) {
            evento = eventoRepository.findById(eventoId).orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
        }

        // remove do agregado e do banco
        evento.removeInscricao(inscricao);
        inscricaoRepository.delete(inscricao);
        // atualiza contagem de inscritos via evento.removeInscricao
        eventoRepository.save(evento);
        return true;
    }

}

