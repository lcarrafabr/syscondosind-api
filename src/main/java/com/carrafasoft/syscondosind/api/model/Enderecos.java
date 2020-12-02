package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.TipoEndereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "enderecos")
public class Enderecos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "endereco_id")
	private Long enderecoId;

	@NotNull
	@Column(length = 9)
	private String cep;

	@NotNull
	@Column(length = 200)
	private String logradouro;

	@NotNull
	@Column(length = 6)
	private String numero;

	@NotNull
	@Column(length = 80)
	private String bairro;

	@NotNull
	@Column(length = 80)
	private String cidade;

	@NotNull
	@Column(length = 2)
	private String estado;

	@Column(length = 150)
	private String complemento;

	@Column(name = "codigo_ibge", length = 10)
	private String codigoIbge;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_endereco", length = 20)
	private TipoEndereco tipoEndereco;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominios;

	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

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

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public Condominios getCondominios() {
		return condominios;
	}

	public void setCondominios(Condominios condominios) {
		this.condominios = condominios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enderecoId == null) ? 0 : enderecoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enderecos other = (Enderecos) obj;
		if (enderecoId == null) {
			if (other.enderecoId != null)
				return false;
		} else if (!enderecoId.equals(other.enderecoId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		parseUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {

		parseUpperCase();
	}

	private void parseUpperCase() {

		logradouro = logradouro.toUpperCase().trim();
		numero = numero.toUpperCase().trim();
		bairro = bairro.toUpperCase().trim();
		cidade = cidade.toUpperCase().trim();
		estado = estado.toUpperCase().trim();
		complemento = complemento.toUpperCase().trim();
		codigoIbge = codigoIbge.toUpperCase().trim();
	}

}
