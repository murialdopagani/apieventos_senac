package com.eventos.senac.apieventos_senac.infra.config;

import com.eventos.senac.apieventos_senac.application.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();

    if (path.equals("/auth/login") || path.equals("/usuario/cadastro") || path.startsWith("/swagger") || path.startsWith(
        "/v3/api-docs") || path.startsWith("/webjars")) {
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.replace("Bearer ", "");
      try {
        // Aqui você pode adicionar a lógica para validar o token JWT
        // Por exemplo, decodificar o token e verificar sua validade
        // Se o token for inválido, lance uma exceção ou retorne um erro
        var jwt = tokenService.validarToken(token);

        var usuario = tokenService.consultarUsuarioPorToken(token);
        //String usuario = jwt.getSubject();
        System.out.println("Usuairo logado" + usuario);

      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token inválido ou expirado");
        //filterChain.doFilter(request, response);
        return;
      }
    } else {
      // Adicionando log para facilitar diagnóstico
      System.out.println("Authorization header ausente ou inválido: " + header);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write("Token não informado ou formato inválido");
      //filterChain.doFilter(request, response);
      return;
    }
    filterChain.doFilter(request, response);

  }
}