package com.javanauta.learningspring.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

	// Chave secreta usada para assinar e verificar tokens JWT
	// Para HS384, a chave deve ter pelo menos 48 caracteres (384 bits)
	private static final String SECRET_KEY = "u8n3kF9vQ2pLz6xR1sT4wV7yB0eH3jK6mN9qP2tU5xA8bC1dE4gH7kL0pS3vZ6y";

	private SecretKey getSigningKey() {
		// Para HS384, a chave precisa ter pelo menos 48 bytes
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	// Gera um token JWT com o nome de usuário e validade de 1 hora
	public String generateToken(String username) {
		return Jwts.builder().header().type("JWT").and()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSigningKey(), SIG.HS384)
				.compact();
	}

	// Extrai as claims do token JWT (informações adicionais do token)
	public Claims extractClaims(String token) {
		try {
			Claims jwt = Jwts.parser()
					.verifyWith(getSigningKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();

			System.out.println("\n\n{" + jwt + "}\n\n");
			return jwt;

		} catch (Exception e) {
			System.out.println("JWT Filter error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// Extrai o nome de usuário do token JWT
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	// Verifica se o token JWT está expirado
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	// Valida o token JWT verificando o nome de usuário e se o token não está
	// expirado
	public boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
}