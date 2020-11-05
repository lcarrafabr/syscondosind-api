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
@Table(name = "apartamentos")
public class Apartamentos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apartamentoId;

	@NotNull
	@Size(max = 20)
	@Column(name = "nome_apartamento", length = 20)
	private String nomeApartamento;

	@NotNull
	private Boolean cobertura;

	public Long getApartamentoId() {
		return apartamentoId;
	}

	public void setApartamentoId(Long apartamentoId) {
		this.apartamentoId = apartamentoId;
	}

	public String getNomeApartamento() {
		return nomeApartamento;
	}

	public void setNomeApartamento(String nomeApartamento) {
		this.nomeApartamento = nomeApartamento;
	}

	public Boolean getCobertura() {
		return cobertura;
	}

	public void setCobertura(Boolean cobertura) {
		this.cobertura = cobertura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apartamentoId == null) ? 0 : apartamentoId.hashCode());
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
		Apartamentos other = (Apartamentos) obj;
		if (apartamentoId == null) {
			if (other.apartamentoId != null)
				return false;
		} else if (!apartamentoId.equals(other.apartamentoId))
			return false;
		return true;
	}

}
