package elducche.projet_3_spring.security;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger LOGGER = Logger.getLogger(JwtUtils.class.getName());

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            LOGGER.severe("Invalid JWT token: signature " + e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.severe("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.severe("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.severe("JWT token is unsupported: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.severe("JWT claims string is empty: {}" + e.getMessage());
        }

        return false;
    }
}
