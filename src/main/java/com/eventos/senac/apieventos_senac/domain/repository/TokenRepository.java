package com.eventos.senac.apieventos_senac.domain.repository;

import com.eventos.senac.apieventos_senac.domain.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

}
