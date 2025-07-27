package com.movie.portal.movie_service;

import com.movie.portal.movie_service.movie.Component.JwtUtils;
import com.movie.portal.movie_service.utility.AppConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtUtils.validateToken(token) != null) {
                    String username = jwtUtils.extractUsername(token);
                    String role = jwtUtils.extractRole(token);

                    if (!role.startsWith(AppConstant.ROLE)) {
                        role = AppConstant.ROLE + role;
                    }

                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                log.error("Invalid token");
                throw new RuntimeException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}