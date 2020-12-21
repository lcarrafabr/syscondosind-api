package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "operadoras")
public class Operadoras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operadora_id")
	private Long operadoraId;

	@NotNull
	@Size(min = 3, max = 45)
	@Column(name = "nome_operadora")
	private String nomeOperadora;

	@NotNull
	@Column(name = "status_ativo")
	private Boolean statusAtivo;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getOperadoraId() {
		return operadoraId;
	}

	public void setOperadoraId(Long operadoraId) {
		this.operadoraId = operadoraId;
	}

	public String getNomeOperadora() {
		return nomeOperadora;
	}

	public void setNomeOperadora(String nomeOperadora) {
		this.nomeOperadora = nomeOperadora;
	}

	public Boolean getStatusAtivo() {
		return statusAtivo;
	}

	public void setStatusAtivo(Boolean statusAtivo) {
		this.statusAtivo = statusAtivo;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operadoraId == null) ? 0 : operadoraId.hashCode());
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
		Operadoras other = (Operadoras) obj;
		if (operadoraId == null) {
			if (other.operadoraId != null)
				return false;
		} else if (!operadoraId.equals(other.operadoraId))
			return false;
		return true;
	}
	
	@PrePersist
	public void aoCadastrar() {
		
		nomeOperadora = nomeOperadora.trim().toUpperCase();
	}
	
	@PreUpdate
	public void aoAtualizar() {
		
		nomeOperadora = nomeOperadora.trim().toUpperCase();
	}

}
