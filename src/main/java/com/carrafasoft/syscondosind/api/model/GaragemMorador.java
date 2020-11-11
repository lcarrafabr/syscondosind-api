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

import com.carrafasoft.syscondosind.api.enums.TipoVaga;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "garagem_morador")
public class GaragemMorador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "garagem_morador_id")
	private Long garagemMoradorId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_vaga", length = 15)
	private TipoVaga tipoVaga;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	@NotNull
	@JsonIgnoreProperties("pessoa")
	@ManyToOne
	@JoinColumn(name = "veiculo_id")
	private Veiculos veiculo;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "vaga_garagem_id")
	private VagasGaragem vagaGaragem;

	public Long getGaragemMoradorId() {
		return garagemMoradorId;
	}

	public void setGaragemMoradorId(Long garagemMoradorId) {
		this.garagemMoradorId = garagemMoradorId;
	}

	public TipoVaga getTipoVaga() {
		return tipoVaga;
	}

	public void setTipoVaga(TipoVaga tipoVaga) {
		this.tipoVaga = tipoVaga;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	public Veiculos getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculos veiculo) {
		this.veiculo = veiculo;
	}

	public VagasGaragem getVagaGaragem() {
		return vagaGaragem;
	}

	public void setVagaGaragem(VagasGaragem vagaGaragem) {
		this.vagaGaragem = vagaGaragem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((garagemMoradorId == null) ? 0 : garagemMoradorId.hashCode());
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
		GaragemMorador other = (GaragemMorador) obj;
		if (garagemMoradorId == null) {
			if (other.garagemMoradorId != null)
				return false;
		} else if (!garagemMoradorId.equals(other.garagemMoradorId))
			return false;
		return true;
	}

}
