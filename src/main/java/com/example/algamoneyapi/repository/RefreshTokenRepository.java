package com.example.algamoneyapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.algamoneyapi.model.RefreshToken;
import com.example.algamoneyapi.model.Usuario;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	  Optional<RefreshToken> findByToken(String token);

	  @Modifying
	  int deleteByUsuario(Usuario usuario);
}
