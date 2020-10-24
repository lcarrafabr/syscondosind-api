package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EnderecoCondominios {

	@Column(length = 8)
	private String cep;
	
	@Column(length = 255)
	private String logradouro;

	@Column(length = 6)
	private String numero;

	@Column(length = 150)
	private String complemento;

	@Column(length = 50)
	private String bairro;

	@Column(length = 50)
	private String cidade;

	@Column(length = 45)
	private String estado;
	@Column(name = "codigo_ibge", length = 10)
	private String codigoIbge;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

}
