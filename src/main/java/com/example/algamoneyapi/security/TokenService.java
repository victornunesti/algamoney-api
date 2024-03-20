package com.example.algamoneyapi.security;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.algamoneyapi.config.property.AlgamoneyApiProperty;
import com.example.algamoneyapi.model.RefreshToken;
import com.example.algamoneyapi.model.Usuario;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class TokenService {
	
	@Autowired
	AlgamoneyApiProperty algamoneyApiProperty;
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(Usuario usuario) {
		
		Map<String, Object> mapaPayload = new HashMap<>();
		mapaPayload.put("nome", usuario.getNome());
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("algamoney")
					.withSubject(usuario.getEmail())
					.withExpiresAt(genExpirationDate())
					.withPayload(mapaPayload)
					.sign(algorithm);
			
			return token;
			
		}catch (JWTCreationException e) {
			throw new RuntimeException("Error ao gerar token", e);
		}
	}
	
	public String validationToken(String token) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("algamoney")
					.build()
					.verify(token)
					.getSubject();			
			
		}catch (JWTVerificationException e) {
			return "";
		}
	}

	public void adicionarCookie(HttpServletRequest req, HttpServletResponse res, RefreshToken refreshToken) {
		
		Cookie cookie = new Cookie("refreshtoken", refreshToken.getToken());
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps()); 
		cookie.setPath(req.getContextPath()+"/auth/refreshtoken");
		cookie.setMaxAge(2592000);
		
		res.addCookie(cookie);
	}
	
	public void retirarCookie(HttpServletRequest req, HttpServletResponse res, String nomeCookie) {
		
		Cookie cookie = new Cookie(nomeCookie, null);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath()+"/auth/refreshtoken");
		cookie.setMaxAge(0);
		
		res.addCookie(cookie);
	}
	
	private Instant genExpirationDate() {
		return Instant.now().plusMillis(20000L);
		//return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
