package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "veiculos")
public class Veiculos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "veiculo_id")
	private Long veiculoId;

	@NotNull
	@Column(length = 80)
	private String marca;

	@NotNull
	@Column(length = 80)
	private String modelo;

	@NotNull
	@Column(length = 8)
	private String placa;

	@NotNull
	private Boolean placaMercosul;

	@NotNull
	@Column(length = 4)
	private String ano;

	@NotNull
	private String cor;

	@NotNull
	@Column(length = 45)
	private String nomeCor;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	public Long getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Boolean getPlacaMercosul() {
		return placaMercosul;
	}

	public void setPlacaMercosul(Boolean placaMercosul) {
		this.placaMercosul = placaMercosul;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getNomeCor() {
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
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
		result = prime * result + ((veiculoId == null) ? 0 : veiculoId.hashCode());
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
		Veiculos other = (Veiculos) obj;
		if (veiculoId == null) {
			if (other.veiculoId != null)
				return false;
		} else if (!veiculoId.equals(other.veiculoId))
			return false;
		return true;
	}

}
