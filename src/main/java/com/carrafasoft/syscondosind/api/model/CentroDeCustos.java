package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "centro_de_custos")
public class CentroDeCustos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "centro_custo_id")
	private Long centroCustoId;

	@NotNull
	@Column(name = "nome_centro_custo", length = 80)
	private String nomeCentroCusto;

	@NotNull
	private Boolean status;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public String getNomeCentroCusto() {
		return nomeCentroCusto;
	}

	public void setNomeCentroCusto(String nomeCentroCusto) {
		this.nomeCentroCusto = nomeCentroCusto;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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
		result = prime * result + ((centroCustoId == null) ? 0 : centroCustoId.hashCode());
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
		CentroDeCustos other = (CentroDeCustos) obj;
		if (centroCustoId == null) {
			if (other.centroCustoId != null)
				return false;
		} else if (!centroCustoId.equals(other.centroCustoId))
			return false;
		return true;
	}

}
