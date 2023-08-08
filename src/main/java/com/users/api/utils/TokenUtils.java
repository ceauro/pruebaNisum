package com.users.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    private static final String ACCESS_TOKEN_SECRET = "1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken(String nombre, String email){
         long expirationTime = System.currentTimeMillis() * ACCESS_TOKEN_VALIDITY_SECONDS;
         Date expirationDate = new Date(expirationTime);

         Map<String, Object> extra = new HashMap<>();
         extra.put("nombre", nombre);

         return Jwts.builder()
                 .setSubject(email)
                 .setExpiration(expirationDate)
                 .addClaims(extra)
                 .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                 .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
        } catch (JwtException jwtException){
            return null;
        }
    }
}
