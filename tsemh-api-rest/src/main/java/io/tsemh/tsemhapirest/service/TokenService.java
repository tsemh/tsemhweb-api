package io.tsemh.tsemhapirest.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.tsemh.tsemhapirest.entity.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.jwt.secret}")
	private String secret;
	
	public String geraToken(Usuario usuario) {
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        return JWT.create()
	            .withIssuer("API tsemh web")
	            .withSubject(usuario.getEmail())
	            .withExpiresAt(dataExpiracao())
	            .sign(algorithm);
	    } catch (JWTCreationException exception) {
	        throw new RuntimeException("Erro ao gerar token JWT", exception);
	    }
	}

	public String getSubject(String tokenJWT) {
		try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		        .withIssuer("API tsemh web")
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		    
		} catch (JWTVerificationException exception){
			throw new RuntimeException("Token JWT inválido ou expirado!");
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
	}

}
