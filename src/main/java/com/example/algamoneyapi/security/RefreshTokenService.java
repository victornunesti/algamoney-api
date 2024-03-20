package com.example.algamoneyapi.security;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoneyapi.model.RefreshToken;
import com.example.algamoneyapi.model.Usuario;
import com.example.algamoneyapi.repository.RefreshTokenRepository;
import com.example.algamoneyapi.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Usuario usuario) {
		
	    RefreshToken refreshToken = new RefreshToken();
	
	    refreshToken.setUsuario(usuarioRepository.findById(usuario.getCodigo()).get());
	    refreshToken.setExpiryDate(Instant.now().plusMillis(36000000L));
	    refreshToken.setToken(UUID.randomUUID().toString());
	
	    refreshToken = refreshTokenRepository.save(refreshToken);
	    return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
	      refreshTokenRepository.delete(token);
	      throw new RuntimeException("Refresh token was expired. Please make a new signin request");
	    }
	
	    return token;
	}
	
	@Transactional
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUsuario(usuarioRepository.findById(userId).get());
	}
	
}