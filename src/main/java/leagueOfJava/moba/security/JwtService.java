package leagueOfJava.moba.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.nio.charset.StandardCharsets;

public class JwtService {

    private final Key SECRET_KEY =
            Keys.hmacShaKeyFor("minha-chave-secreta-super-segura-123456".getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }
}