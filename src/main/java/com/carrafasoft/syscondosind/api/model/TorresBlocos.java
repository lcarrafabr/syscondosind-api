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
@Table(name = "torres_blocos")
public class TorresBlocos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "torre_bloco_id")
	private Long torreBlocoId;

	@NotNull
	@Size(max = 45)
	@Column(name = "nome_bloco", length = 45)
	private String nomeBloco;

	@NotNull
	@Column(name = "possui_elevador")
	private Boolean possuiElevador;

	public Long getTorreBlocoId() {
		return torreBlocoId;
	}

	public void setTorreBlocoId(Long torreBlocoId) {
		this.torreBlocoId = torreBlocoId;
	}

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public Boolean getPossuiElevador() {
		return possuiElevador;
	}

	public void setPossuiElevador(Boolean possuiElevador) {
		this.possuiElevador = possuiElevador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((torreBlocoId == null) ? 0 : torreBlocoId.hashCode());
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
		TorresBlocos other = (TorresBlocos) obj;
		if (torreBlocoId == null) {
			if (other.torreBlocoId != null)
				return false;
		} else if (!torreBlocoId.equals(other.torreBlocoId))
			return false;
		return true;
	}

}
