package com.sparta.oceanbackend.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 3600000; // 1시간

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Long userId, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("name", name);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    public String getNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("name", String.class);
    }

    public String resolveToken(HttpServletRequest request) {
        Cookie tokenCookie = getTokenCookie(request);
        return tokenCookie != null ? tokenCookie.getValue() : null;
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) { // 토큰이 만료된 경우 처리
            return false;
        } catch (Exception e) { // 기타 예외 처리
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Cookie getTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private void parseToken(String token) {
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token);
    }
}
