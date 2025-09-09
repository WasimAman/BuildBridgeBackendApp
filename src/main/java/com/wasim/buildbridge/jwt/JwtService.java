package com.wasim.buildbridge.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.wasim.buildbridge.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    public String getToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.CLAIM_EMAIL, user.getEmail());
        claims.put(JwtConstant.CLAIM_FULLNAME, user.getFullName());
        claims.put(JwtConstant.CLAIM_ID, user.getId());

        return generateToken(claims, user.getUsername());
    }

    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME))
                .signWith(getSigninKey())
                .compact();
    }

    public String extractTokenFromHeader(String jwtHeader) {
        if (jwtHeader != null && jwtHeader.startsWith(JwtConstant.JWT_PREFIX)) {
            return jwtHeader.substring(JwtConstant.JWT_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
    }

    public Object getClaim(String token, String claim) {
        Claims allClaims = extractAllClaims(token);
        return allClaims.get(claim);
    }

    public String getSubjectFromToken(){
        return extractAllClaims(getSubjectFromToken()).getSubject();
    }
}
