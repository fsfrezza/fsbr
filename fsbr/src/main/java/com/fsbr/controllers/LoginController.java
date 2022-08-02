package com.fsbr.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsbr.entities.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
public class LoginController {

	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	@PostMapping("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(@RequestBody Usuario usuario) {
		try {
			if (usuario.getUsuario().equals("frezza@fsbr.com.br") && usuario.getSenha().equals("1234")) {
				String jwtToken = Jwts.builder().setSubject(usuario.getUsuario()).setIssuer("localhost:8080")
						.setIssuedAt(new Date())
						.setExpiration(Date
								.from(LocalDateTime.now().plusMinutes(15L).atZone(ZoneId.systemDefault()).toInstant()))
						.signWith(key).compact();

				return jwtToken;
			} else
				return "Usuário e/ou senha inválidos.";
		} catch (Exception ex) {
			return "Erro interno.";
		}
	}

}