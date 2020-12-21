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
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.TipoTelefoneEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "telefones")
public class Telefones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "telefone_id")
	private Long telefoneId;

	@NotNull
	@Column(length = 10)
	private String ddd;

	@NotNull
	@Size(max = 10)
	@Column(length = 10)
	private String numero;

	@Column(length = 10)
	private String ramal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_telefone", length = 20)
	private TipoTelefoneEnum tipoTelefone;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "operadora_id")
	private Operadoras operadora;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	public Long getTelefoneId() {
		return telefoneId;
	}

	public void setTelefoneId(Long telefoneId) {
		this.telefoneId = telefoneId;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public TipoTelefoneEnum getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefoneEnum tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Operadoras getOperadora() {
		return operadora;
	}

	public void setOperadora(Operadoras operadora) {
		this.operadora = operadora;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telefoneId == null) ? 0 : telefoneId.hashCode());
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
		Telefones other = (Telefones) obj;
		if (telefoneId == null) {
			if (other.telefoneId != null)
				return false;
		} else if (!telefoneId.equals(other.telefoneId))
			return false;
		return true;
	}

}
