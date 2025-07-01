package com.javanauta.learningspring.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

	// Chave secreta usada para assinar e verificar tokens JWT
	private final String secretKey = "sua-chave-secreta-super-segura-que-deve-ser-bem-longa";

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	// Gera um token JWT com o nome de usuário e validade de 1 hora
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSigningKey())
				.compact();
	}

	// Extrai as claims do token JWT (informações adicionais do token)
	public Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
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