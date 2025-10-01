package com.eventos.senac.apieventos_senac.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eventos.senac.apieventos_senac.dto.LoginRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.Token;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.repository.TokenRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${spring.palavrapasse}")
    private String secret;

    private String emissor = "Murialdo";

    @Value("${spring.sessao}")
    private Long tempo;

    public String generateToken(LoginRequestDto login) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var usuario = usuarioRepository.findByEmail(login.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            var dataExpiracao = this.gerarDataExpiracao();
            String token = JWT.create()
                    .withIssuer(emissor)
                    .withSubject(login.email())
                    .withExpiresAt(dataExpiracao.plusHours(15).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
            this.salvarToken(token,dataExpiracao,usuario);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public DecodedJWT validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(emissor)
                    .build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }

    public void salvarToken(String token, LocalDateTime dataExpiracao, Usuario usuario){
        var tokenBanco = new Token();
        tokenBanco.setToken(token);
        tokenBanco.setDataExpiracao(dataExpiracao);
        tokenBanco.setUsuario(usuario);

        tokenRepository.save(tokenBanco);
    }


    private LocalDateTime gerarDataExpiracao() {
        var dataAtual = LocalDateTime.now();
        var dataFutura = dataAtual.plusMinutes(tempo);
        return dataFutura;
    }

    public Usuario consultarUsuarioPorToken(String token) throws Exception {
        var tokenBanco = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token não encontrado"));

        if(tokenBanco.getDataExpiracao().isBefore(LocalDateTime.now())){
            throw new Exception("Token Expirado.!");
        }
        tokenBanco.setDataExpiracao(LocalDateTime.now().plusMinutes(tempo));
        tokenRepository.save(tokenBanco);
        return tokenBanco.getUsuario();
    }

}