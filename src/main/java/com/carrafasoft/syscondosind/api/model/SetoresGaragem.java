package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.StatusSetor;

@Entity
@Table(name = "setores_garagem")
public class SetoresGaragem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "setor_garagem_id")
	private Long setorGaragemId;

	@NotNull
	@Column(name = "nome_setor", length = 45)
	private String nomeSetor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusSetor statusSetor;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	public Long getSetorGaragemId() {
		return setorGaragemId;
	}

	public void setSetorGaragemId(Long setorGaragemId) {
		this.setorGaragemId = setorGaragemId;
	}

	public String getNomeSetor() {
		return nomeSetor;
	}

	public void setNomeSetor(String nomeSetor) {
		this.nomeSetor = nomeSetor;
	}

	public StatusSetor getStatusSetor() {
		return statusSetor;
	}

	public void setStatusSetor(StatusSetor statusSetor) {
		this.statusSetor = statusSetor;
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
		result = prime * result + ((setorGaragemId == null) ? 0 : setorGaragemId.hashCode());
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
		SetoresGaragem other = (SetoresGaragem) obj;
		if (setorGaragemId == null) {
			if (other.setorGaragemId != null)
				return false;
		} else if (!setorGaragemId.equals(other.setorGaragemId))
			return false;
		return true;
	}

}
