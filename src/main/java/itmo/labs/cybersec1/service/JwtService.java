package itmo.labs.cybersec1.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:3600000}") long expirationMillis) {
        SecretKey derivedKey;
        long exp = expirationMillis;
        try {
            if (secret == null || secret.isBlank()) {
                throw new IllegalArgumentException("JWT secret is empty");
            }
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            derivedKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (RuntimeException ex) {
            log.warn("Invalid JWT secret provided. Falling back to a generated key. Please set a valid BASE64-encoded 256-bit key in app.jwt.secret.", ex);
            derivedKey = Jwts.SIG.HS256.key().build();
        }
        this.secretKey = derivedKey;
        this.expirationMillis = exp;
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        if (!username.equals(userDetails.getUsername())) return false;
        Date exp = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
        return exp.after(new Date());
    }
}
