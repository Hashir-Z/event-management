package com.software.apigateway.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import com.software.apigateway.exception.JwtTokenIncorrectStructureException;
import com.software.apigateway.exception.JwtTokenMalformedException;
import com.software.apigateway.exception.JwtTokenMissingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public void validateToken (String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            String[] parts = token.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
            }
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(parts[1]);
        } catch (SignatureException signatureException) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
