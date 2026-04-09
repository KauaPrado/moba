package leagueOfJava.moba.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // 👈 Importação nova
import leagueOfJava.moba.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key; // 👈 Importação nova
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Método auxiliar para gerar a Key a partir do texto puro
    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuer("moba-api")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 👈 Usando a Key gerada
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder() // 👈 parserBuilder() é o padrão mais novo
                    .setSigningKey(getSigningKey()) // 👈 Usando a Key gerada
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();

        } catch (Exception e) {
            return null;
        }
    }
}