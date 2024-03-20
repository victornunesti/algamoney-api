package com.example.algamoneyapi.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="refreshtoken")
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@OneToOne
	@JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo")
	private Usuario usuario;
	
	@Column(name="token", nullable = false, unique = true)
	private String token;
	
	@Column(name="expiry_date", nullable = false)
	private Instant expiryDate;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Instant getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

}