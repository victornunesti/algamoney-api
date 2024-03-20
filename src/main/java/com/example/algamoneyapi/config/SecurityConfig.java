package com.example.algamoneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//http.authorizeHttpRequests((authz).anyRequest().authenticated()).httpBasic(withDefaults());	
		
		return http
			   .csrf(csrf -> csrf.disable())
			   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			   .authorizeHttpRequests(authorize -> authorize
					   .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					   .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
					   .requestMatchers(HttpMethod.POST, "/auth/refreshtoken").permitAll()
					   .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
					   .requestMatchers(HttpMethod.GET, "/categorias").hasRole("PESQUISAR_CATEGORIA")
					   .requestMatchers(HttpMethod.POST, "/categorias").hasRole("CADASTRAR_CATEGORIA")
					   .requestMatchers(HttpMethod.PUT, "/categorias").hasRole("CADASTRAR_CATEGORIA")
					   .requestMatchers(HttpMethod.GET, "/pessoas").hasRole("PESQUISAR_PESSOA")
					   .requestMatchers(HttpMethod.POST, "/pessoas").hasRole("CADASTRAR_PESSOA")
					   .requestMatchers(HttpMethod.PUT, "/pessoas").hasRole("CADASTRAR_PESSOA")
					   .requestMatchers(HttpMethod.DELETE, "/pessoas").hasRole("REMOVER_PESSOA")
					   .requestMatchers(HttpMethod.GET, "/lancamentos").hasRole("PESQUISAR_LANCAMENTO")
					   .requestMatchers(HttpMethod.POST, "/lancamentos").hasRole("CADASTRAR_LANCAMENTO")
					   .requestMatchers(HttpMethod.PUT, "/lancamentos").hasRole("CADASTRAR_LANCAMENTO")
					   .requestMatchers(HttpMethod.DELETE, "/lancamentos").hasRole("REMOVER_LANCAMENTO")					   
					   .anyRequest().authenticated()
				)
			   .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			   .build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/categorias", "/ignore2");
    }*/

}
