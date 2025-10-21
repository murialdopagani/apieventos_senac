package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.Inscricao;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.domain.repository.InscricaoRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusPresenca;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import java.util.List;
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
     * Inscreve um usuário em um evento. Cria a entidade Inscricao mínima (usuario) e delega a validação para o domínio
     * (Evento.inscrever). Retorna o EventoResponseDto atualizado.
     */
    @Transactional
    public InscricaoResponseDto inscrever(InscricaoRequestDto inscricaoRequestDto) {
        var usuario = usuarioService.buscarPorIdObj(inscricaoRequestDto.usuarioId());

        var evento = eventoRepository.findById(inscricaoRequestDto.eventoId())
            .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));

        if (inscricaoRepository.findByEventoIdAndUsuarioId(inscricaoRequestDto.eventoId(), inscricaoRequestDto.usuarioId())
            .isPresent()) {
            throw new ValidacoesRegraNegocioException("Participante já inscrito neste evento");
        }

        var inscricao = new Inscricao(inscricaoRequestDto, usuario, evento);

        try {
            evento.inscrever(inscricao);  // validações de domínio
            inscricaoRepository.save(inscricao);
            // garante persistência dos contadores no evento (ex.: inscritos = tamanho da lista)
            eventoRepository.save(evento);
        } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException ex) {
            throw new ValidacoesRegraNegocioException("Não foi possível concluir a inscrição por conflito. Tente novamente.");
        }

        return new InscricaoResponseDto(inscricao);
    }


    @Transactional
    public InscricaoResponseDto confirmarInscricao(Long inscricaoId) {
        var inscricao = inscricaoRepository.findByIdAndStatusPresencaNot(inscricaoId, EnumStatusPresenca.CANCELADO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Inscrição não encontrada"));

        if (inscricao.getStatusPresenca() == EnumStatusPresenca.CONFIRMADO) {
            throw new ValidacoesRegraNegocioException("Inscrição já está Confirmada..!");
        }

        Evento evento = inscricao.getEvento();
        if (evento == null) {
            throw new ValidacoesRegraNegocioException("Inscrição sem evento associado");
        }

        try {
            // valida e atualiza contadores no domínio (considera apenas confirmados)
            if (evento.isFull()) {
                throw new ValidacoesRegraNegocioException("Capacidade máxima atingida para confirmações");
            }
            evento.confirmarPresenca(inscricao);

            // atualiza status da inscrição para confirmado
            alterarStatusInscricao(inscricao, EnumStatusPresenca.CONFIRMADO);

            // persiste contadores do evento
            eventoRepository.save(evento);
        } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException ex) {
            throw new ValidacoesRegraNegocioException("Não foi possível confirmar por conflito. Tente novamente.");
        }

        return new InscricaoResponseDto(inscricao);
    }

    /**
     * Cancela (soft) uma inscrição: marca status COMO CANCELADO e atualiza contadores no evento.
     */
    @Transactional
    public InscricaoResponseDto cancelarInscricao(Long inscricaoId) {
        var inscricao = inscricaoRepository.findById(inscricaoId)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Inscrição não encontrada"));

        if (inscricao.getStatusPresenca() == EnumStatusPresenca.CANCELADO) {
            throw new ValidacoesRegraNegocioException("Inscrição já está cancelada");
        }

        Evento evento = inscricao.getEvento();
        try {
            // marca como cancelada e persiste inscrição (salva via alterarStatusInscricao)
            alterarStatusInscricao(inscricao, EnumStatusPresenca.CANCELADO);

            // atualiza contadores do evento (se existir)
            if (evento != null) {
                // recomputa com base na coleção de inscrições
                evento.recomputeContadores();
                eventoRepository.save(evento);
            }

        } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException ex) {
            throw new ValidacoesRegraNegocioException("Não foi possível cancelar a inscrição por conflito. Tente novamente.");
        }

        return new InscricaoResponseDto(inscricao);
    }

    /**
     * Lista todas as inscrições (read-only).
     */
    @Transactional(readOnly = true)
    public List<InscricaoResponseDto> listarTodos() {
        return inscricaoRepository.findAll()
            .stream()
            .map(InscricaoResponseDto::new)
            .toList();
    }

    /**
     * Lista todas as inscrições de um evento específico (read-only).
     */
    @Transactional(readOnly = true)
    public List<InscricaoResponseDto> listarPorEvento(Long eventoId) {
        // valida existência do evento
        eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));

        return inscricaoRepository.findByEventoId(eventoId)
                .stream()
                .map(InscricaoResponseDto::new)
                .toList();
    }

    private void alterarStatusInscricao(Inscricao inscricao, EnumStatusPresenca statusPresenca) {
        inscricao.setStatusPresenca(statusPresenca);
        inscricaoRepository.save(inscricao);
    }
}
