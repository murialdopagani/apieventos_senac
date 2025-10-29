package com.eventos.senac.apieventos_senac.infra.config;

import com.eventos.senac.apieventos_senac.application.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

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
                var jwt = tokenService.validarToken(token);
                var usuario = tokenService.consultarUsuarioPorToken(token);
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(usuario,
                        null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(auth);

                //System.out.println("Usuairo logado" + usuario);
            } catch (Exception e) {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "Token inválido ou expirado");
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //response.getWriter().write("Token inválido ou expirado");
                //filterChain.doFilter(request, response);
                return;
            }
        } else {
            // Adicionando log para facilitar diagnóstico
            System.out.println("Authorization header ausente ou inválido: " + header);
            writeErrorResponse(response, HttpStatus.FORBIDDEN.value(), "Token não informado ou formato inválido");
            //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //response.getWriter().write("Token não informado ou formato inválido");
            //filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    private void writeErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        Map<String, Object> body = Map.of(
            "status", status,
            "error", message
        );
        objectMapper.writeValue(response.getWriter(), body);
    }
}