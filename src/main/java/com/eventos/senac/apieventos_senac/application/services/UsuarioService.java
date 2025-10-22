package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Administrador;
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

    public Usuario buscarPorIdObj(Long id) {
        return usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
    }


    public UsuarioResponseDto salvarUsuario(UsuarioCriarRequestDto usuarioRequestDto) {
        var usuario = usuarioRepository.findByCpf_CpfAndStatusNot(String.valueOf(new CPF(usuarioRequestDto.cpf())),
            EnumStatusUsuario.EXCLUIDO).orElse(null);
        if (usuario == null) {
            if (usuarioRequestDto.isAdm()){
                usuario = new Administrador(usuarioRequestDto);
            } else {
                usuario = new Usuario(usuarioRequestDto);
            }
        }

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

    public boolean excluirUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        alterarStatusUsuario(usuario, EnumStatusUsuario.EXCLUIDO);
        return true;
    }


    public boolean desbloquearUsuario(Long id) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        alterarStatusUsuario(usuario, EnumStatusUsuario.ATIVO);
        return true;
    }

    public boolean bloquearUsuario(Long id) {
        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
        alterarStatusUsuario(usuario, EnumStatusUsuario.BLOQUEADO);
        return true;
    }

    private void alterarStatusUsuario(Usuario usuario, EnumStatusUsuario statusUsuario) {
        usuario.setStatus(statusUsuario);
        usuarioRepository.save(usuario);
    }
}