package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.repository.UsuarioRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.CPF;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAllByStatusNotOrderById(EnumStatusUsuario.EXCLUIDO)
            .stream()
            .map(UsuarioResponseDto::new)
            .collect(Collectors.toList());
    }

    public UsuarioResponseDto buscarPorId(Long id) {
        return usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .stream()
            .map(UsuarioResponseDto::new)
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
    }

    public UsuarioResponseDto salvarUsuario(UsuarioCriarRequestDto usuarioRequestDto) {
        var usuario = usuarioRepository.findByCpf_CpfAndStatusNot(String.valueOf(new CPF(usuarioRequestDto.cpf())),
            EnumStatusUsuario.EXCLUIDO).orElse(new Usuario(usuarioRequestDto));
        if (usuario.getId() != null) {
            usuario = usuario.atualizarUsuarioFromDTO(usuario, usuarioRequestDto);
        }
        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioCriarRequestDto usuarioRequestDto) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        usuario = usuario.atualizarUsuarioFromDTO(usuario, usuarioRequestDto);
        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto deletarUsuario(Long id) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        usuario.setStatus(EnumStatusUsuario.EXCLUIDO);
        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto desbloquearUsuario(Long id) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        usuario.setStatus(EnumStatusUsuario.ATIVO);
        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto bloquearUsuario(Long id) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        usuario.setStatus(EnumStatusUsuario.BLOQUEADO);
        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }
}