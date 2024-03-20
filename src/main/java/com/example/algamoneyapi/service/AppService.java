package com.example.algamoneyapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.TypedQuery;

@Service
public class AppService {
	
	public void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		
		//duvida
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

}
