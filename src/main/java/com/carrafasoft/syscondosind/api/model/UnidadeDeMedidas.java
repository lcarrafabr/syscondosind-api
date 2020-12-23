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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "unidade_de_medidas")
public class UnidadeDeMedidas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unidade_de_medida_id")
	private Long unidadeDeMedidaId;

	@NotNull
	@Column(name = "nome_unidade_medida", length = 45)
	private String nomeUnidadeMedida;

	@NotNull
	@Column(length = 4)
	private String sigla;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getUnidadeDeMedidaId() {
		return unidadeDeMedidaId;
	}

	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
		this.unidadeDeMedidaId = unidadeDeMedidaId;
	}

	public String getNomeUnidadeMedida() {
		return nomeUnidadeMedida;
	}

	public void setNomeUnidadeMedida(String nomeUnidadeMedida) {
		this.nomeUnidadeMedida = nomeUnidadeMedida;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
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
		result = prime * result + ((unidadeDeMedidaId == null) ? 0 : unidadeDeMedidaId.hashCode());
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
		UnidadeDeMedidas other = (UnidadeDeMedidas) obj;
		if (unidadeDeMedidaId == null) {
			if (other.unidadeDeMedidaId != null)
				return false;
		} else if (!unidadeDeMedidaId.equals(other.unidadeDeMedidaId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {
		toUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {
		toUpperCase();
	}

	private void toUpperCase() {

		if (nomeUnidadeMedida != null && !nomeUnidadeMedida.isEmpty()) {
			nomeUnidadeMedida = nomeUnidadeMedida.trim().toUpperCase();
		}
		if (sigla != null && !sigla.isEmpty()) {
			sigla = sigla.trim().toUpperCase();
		}
	}

}
