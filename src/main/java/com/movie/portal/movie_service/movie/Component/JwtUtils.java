package com.movie.portal.movie_service.movie.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.movie.portal.movie_service.utility.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    public String extractRole(String token) {
        return validateToken(token).getClaim(AppConstant.ROLES).asList(String.class).get(0);
    }
}
