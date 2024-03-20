package com.example.algamoneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.algamoneyapi.exception.EmptyResultDataAccessException;
import com.example.algamoneyapi.exception.ExceptionResponse;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String mensagem = messageSource.getMessage("mensagem.invalida", null,LocaleContextHolder.getLocale());
		String mensagemDev = ex.getCause()!=null?ex.getCause().toString():ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagem, mensagemDev));
				
		return super.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex,  WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(null, ex.getMessage(), null, new Date());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
		
		String mensagem = messageSource.getMessage("recurso.nao-encontrado", null,LocaleContextHolder.getLocale());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem+" "+ex.getMessage());
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		
		String mensagem = messageSource.getMessage("recurso.operacao-nao-permitida", null,LocaleContextHolder.getLocale());
		String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
		
		List<Erro> erros = Arrays.asList(new Erro(mensagem,mensagemDev));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError:bindingResult.getFieldErrors()) {
			String mensagemDev = fieldError.toString();
			String mensagemUsu = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			
			erros.add(new Erro(mensagemUsu, mensagemDev));
		}
		
		
		return erros;
	}
	
	public static class Erro{
		
		private String menssagemUsuario;
		
		private String menssagemDesenvolvedor;

		public Erro(String menssagemUsuario, String menssagemDesenvolvedor) {
			super();
			this.menssagemUsuario = menssagemUsuario;
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
		}

		public String getMenssagemUsuario() {
			return menssagemUsuario;
		}

		public void setMenssagemUsuario(String menssagemUsuario) {
			this.menssagemUsuario = menssagemUsuario;
		}

		public String getMenssagemDesenvolvedor() {
			return menssagemDesenvolvedor;
		}

		public void setMenssagemDesenvolvedor(String menssagemDesenvolvedor) {
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
		}
		
		
		
	}

}
