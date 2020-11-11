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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.StatusSetor;

@Entity
@Table(name = "vagas_garagem")
public class VagasGaragem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vaga_garagem_id")
	private Long vagaGaragemId;

	@NotNull
	@Column(name = "nome_vaga", length = 45)
	private String nomeVaga;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusSetor statusVaga;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "setor_garagem_id")
	private SetoresGaragem setor;

	public Long getVagaGaragemId() {
		return vagaGaragemId;
	}

	public void setVagaGaragemId(Long vagaGaragemId) {
		this.vagaGaragemId = vagaGaragemId;
	}

	public String getNomeVaga() {
		return nomeVaga;
	}

	public void setNomeVaga(String nomeVaga) {
		this.nomeVaga = nomeVaga;
	}

	public StatusSetor getStatusVaga() {
		return statusVaga;
	}

	public void setStatusVaga(StatusSetor statusVaga) {
		this.statusVaga = statusVaga;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SetoresGaragem getSetor() {
		return setor;
	}

	public void setSetor(SetoresGaragem setor) {
		this.setor = setor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vagaGaragemId == null) ? 0 : vagaGaragemId.hashCode());
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
		VagasGaragem other = (VagasGaragem) obj;
		if (vagaGaragemId == null) {
			if (other.vagaGaragemId != null)
				return false;
		} else if (!vagaGaragemId.equals(other.vagaGaragemId))
			return false;
		return true;
	}

}
