package com.example.algamoneyapi.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.filter.PessoaFilter;
import com.example.algamoneyapi.service.AppService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private AppService appService;

	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		
		appService.adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));

	}
	
	private Long total(PessoaFilter pessoaFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();		
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.hasText(pessoaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%"+ pessoaFilter.getNome().toLowerCase() +"%"));
		}
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	

}
