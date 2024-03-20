package com.example.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoneyapi.exception.EmptyResultDataAccessException;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.repository.PessoaRepository;
import com.example.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

import jakarta.validation.Valid;

@Service
public class LancamentoService {
	
	@Autowired
	public PessoaRepository pessoaRepository;
	
	@Autowired
	public LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = null;
		
		if(lancamento.getPessoa() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElse(null);			
		}
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
		
	}

	public Lancamento atualizar(Long codigo, @Valid Lancamento lancamento) {
		
		Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
		
		if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento.getPessoa());
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
				
		return lancamentoRepository.save(lancamentoSalvo);
		
	}

	private void validarPessoa(Pessoa pessoa) {
		
		Pessoa pessoaRetorno = null;
		
		if(pessoa.getCodigo()!=null) {
			pessoaRetorno = pessoaRepository.findById(pessoa.getCodigo()).orElse(null);
		}
		
		if(pessoaRetorno==null || pessoaRetorno.isInativo()) {
			throw new PessoaInexistenteOuInativaException();			
		}
		
	}

	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		
		Lancamento lancamento = lancamentoRepository.findById(codigo).orElse(null);
		
		if(lancamento == null) {
			throw new EmptyResultDataAccessException(codigo.toString());  
		}
		
		return lancamento;
	}

}
