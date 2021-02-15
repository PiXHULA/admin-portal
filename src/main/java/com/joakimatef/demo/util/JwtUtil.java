package com.joakimatef.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * JwtUtil - implementation class for creating and making sure the JSON WEB TOKEN is
 * made by this backend and not expired
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */
@Service
public class JwtUtil {

    /**
     * String for complementing the JWT, used in order to be able easy to decrypt by others than this backend
     */
    final String secretStringkey = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";

    /**
     * SecretKey for complementing the JWT, signing the created token
     **/
    final SecretKey key = Keys.hmacShaKeyFor(secretStringkey.getBytes(StandardCharsets.UTF_8));

    /**
     * Calls the @method extractClaim and retrieves the username
     * @param token JWT sent for extracting Username
     * @return String username
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Calls the {@link} method extractClaims and retrieves the username
     * @param token JWT sent for checking that the token has not expired
     * @return Date returns the expiration date
     */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Calls the {@link Function} Function interface that accepts one argument and produces a result.
     * @param token JWT sent for checking that the token has not expired
     * @return Returns function together with the function argument result
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Calls the {@link Claims} Function interface that accepts one argument and produces a result.
     * @param token JWT sent for checking that the token has not expired
     * @return Returns Claims created by Jwts factory method
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Calls the {@link } createToken method.
     * @param userDetails JWT sent for checking that the token has not expired
     * @return Returns a String through createToken with a generated jwt
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Calls the {@link Jwts} class
     * @param claims Map containing Key and Value for specific jwt factory operations.
     * @param subject String username from userDetails stored in subject
     * @return Returns a jwt String
     */
    private String createToken (Map<String, Object> claims, String subject)  {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //10 hours
                .signWith(key).compact();
    }
}
