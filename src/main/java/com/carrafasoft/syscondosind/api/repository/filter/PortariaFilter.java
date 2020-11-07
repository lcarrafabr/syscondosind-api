package com.carrafasoft.syscondosind.api.repository.filter;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class PortariaFilter {
	
	private String nomeVisitante;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataEntradaDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataEntradaAte;

	public String getNomeVisitante() {
		return nomeVisitante;
	}

	public void setNomeVisitante(String nomeVisitante) {
		this.nomeVisitante = nomeVisitante;
	}

	public LocalDateTime getDataEntradaDe() {
		return dataEntradaDe;
	}

	public void setDataEntradaDe(LocalDateTime dataEntradaDe) {
		this.dataEntradaDe = dataEntradaDe;
	}

	public LocalDateTime getDataEntradaAte() {
		return dataEntradaAte;
	}

	public void setDataEntradaAte(LocalDateTime dataEntradaAte) {
		this.dataEntradaAte = dataEntradaAte;
	}
	
	

}
