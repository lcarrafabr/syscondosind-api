package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bancos")
public class Bancos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banco_id")
	private Long bancoId;

	@NotNull
	@Column(name = "nome_banco", length = 100)
	private String nomeBanco;

	@NotNull
	@Size(max = 5)
	@Column(name = "digito_banco", length = 5)
	private String digitoBanco;

	@NotNull
	private Boolean status;

	private String site;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public String getDigitoBanco() {
		return digitoBanco;
	}

	public void setDigitoBanco(String digitoBanco) {
		this.digitoBanco = digitoBanco;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bancoId == null) ? 0 : bancoId.hashCode());
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
		Bancos other = (Bancos) obj;
		if (bancoId == null) {
			if (other.bancoId != null)
				return false;
		} else if (!bancoId.equals(other.bancoId))
			return false;
		return true;
	}

}
