package com.software.auth.repository;

import com.software.auth.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    @Query(value = """
        SELECT
            t.*
        FROM token t
        INNER JOIN users u on t.user_id = u.id
        WHERE t.expired = false AND t.revoked = false AND u.id = :userId
    """, nativeQuery = true)
    List<Token> findAllValidTokenByUserId (@Param("userId") String userId);
}
