package com.sparta.oceanbackend.api.filter;

import com.sparta.oceanbackend.api.handler.AuthenticationHandler;
import com.sparta.oceanbackend.api.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationHandler authenticationHandler;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthenticationHandler authenticationHandler) {
        this.jwtUtil = jwtUtil;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
        throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            authenticationHandler.handleAuthentication(token, request);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }
}
