package com.example.algamoneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResumoLancamentoDTO(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagmaento, 
		BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {

}
