package com.sparta.oceanbackend.api.handler;

import com.sparta.oceanbackend.api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthenticationHandler(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public void handleAuthentication(String token, HttpServletRequest request) {
        Claims claims = jwtUtil.getClaimsFromToken(token);
        String name = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
