package com.example.algamoneyapi.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String message;
	private String details;
	private Date timestamp;
	
	public ExceptionResponse(Long codigo, String message, String details, Date timestamp) {
		super();
		this.codigo = codigo;
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}

	public ExceptionResponse(Long codigo) {
		super();
		this.codigo = codigo;
	}

	public ExceptionResponse(String message) {
		super();
		this.message = message;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}	

}
