package com.jevprentice.security;

import com.jevprentice.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Component to interact with Json Web Tokens.
 */
@Component
@Slf4j
public class JsonWebTokenProvider {

    /**
     * Generate a JSON web token for an already authenticated user.
     *
     * @param authentication The authenticated user.
     * @return The generated JSON web token.
     */
    public String generateToken(final Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final String userIdString = Long.toString(user.getId());

        final Date now = new Date(System.currentTimeMillis());
        final Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", userIdString);
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setSubject(userIdString)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    /**
     * Validate a JSON web token.
     *
     * @param token The token to be validated.
     * @return Boolean result of the validation, true == validated.
     */
    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (final SignatureException ex) {
            log.error("Invalid JWT Signature");
        } catch (final MalformedJwtException ex) {
            log.error("Invalid JWT Token");
        } catch (final ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (final UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (final IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    /**
     * Finds a user ID from a token.
     *
     * @param token The token.
     * @return The user ID.
     */
    public Long getUserIdFromJWT(final String token) {
        final Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
        final String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
