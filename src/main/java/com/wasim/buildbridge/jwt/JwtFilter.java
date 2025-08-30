package com.wasim.buildbridge.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwtHeader = request.getHeader(JwtConstant.JWT_HEADER);
            String token = jwtService.extractTokenFromHeader(jwtHeader);

            if(token != null){
                if(jwtService.validateToken(token)){
                    
                    String email = jwtService.getClaim(token,JwtConstant.CLAIM_EMAIL).toString();

                    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.emptyList()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else{
                        throw new JwtException("Authentication failed");
                    }
                }else{
                    throw new JwtException("Invalid or expired token!");
                }
            }else{
                throw new JwtException("Missing token!");
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \""+ex.getMessage()+" \"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("null")
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getRequestURI();
        System.out.println(url);
        return url.startsWith("/auth/");
    }
    
}
