package com.example.algamoneyapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoneyapi.model.AuthenticationDTO;
import com.example.algamoneyapi.model.LoginResponseDTO;
import com.example.algamoneyapi.model.RefreshToken;
import com.example.algamoneyapi.model.RegistreDTO;
import com.example.algamoneyapi.model.Usuario;
import com.example.algamoneyapi.repository.UsuarioRepository;
import com.example.algamoneyapi.security.RefreshTokenService;
import com.example.algamoneyapi.security.TokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data, HttpServletRequest req, HttpServletResponse res) {
		var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		var token = tokenService.generateToken((Usuario) auth.getPrincipal());
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken((Usuario) auth.getPrincipal());
		
		tokenService.adicionarCookie(req, res, refreshToken);
				
		return ResponseEntity.ok(new LoginResponseDTO(token));  
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegistreDTO data) {
		
		if(this.usuarioRepository.findByEmail(data.email())!=null)return ResponseEntity.badRequest().build();
		
		String encryptedSenha = new BCryptPasswordEncoder().encode(data.senha());
		Usuario usuario = new Usuario(data.nome(), data.email(), encryptedSenha, data.permissoes());
		
		this.usuarioRepository.save(usuario);
		
		return ResponseEntity.ok().build();
	}	
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest req) {
		
		String refreshToken = null;
		
		if(req.getCookies()!=null &&
				req.getRequestURI().equalsIgnoreCase("/auth/refreshtoken")) {
			for(Cookie cookie:req.getCookies()) {
				if(cookie.getName().equals("refreshtoken")) {
					refreshToken = cookie.getValue();
				}
			}
		}
		
	    return refreshTokenService.findByToken(refreshToken)
	    		.map(refreshTokenService::verifyExpiration)
	    		.map(RefreshToken::getUsuario)
	    		.map(usuario -> {
	  	          String token = tokenService.generateToken(usuario);
	  	          return ResponseEntity.ok(new LoginResponseDTO(token));
	  	        })
	    		.orElseThrow(() -> new RuntimeException(
	    	            "Refresh token is not in database!"));
	    	
	  }
	
	@DeleteMapping("/revoketoken")
	public void revoke(HttpServletRequest req, HttpServletResponse res) {
		
		tokenService.retirarCookie(req, res, "refreshtoken");
		
		res.setStatus(HttpStatus.NO_CONTENT.value());
		
	}

}
