package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDate;

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

import com.carrafasoft.syscondosind.api.enums.TipoMorador;

@Entity
@Table(name = "moradores")
public class Moradores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "morador_id")
	private Long moradorId;

	@Column(length = 14)
	private String cpf;

	@Column(length = 20)
	private String rg;

	@NotNull
	@Column(name = "possui_veiculo")
	private Boolean possuiVeiculo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_morador", length = 15)
	private TipoMorador tipoMorador;

	@NotNull
	private LocalDate dataNascimento;

	// @ManyToOne
	// @JoinColumn(name = "pessoa_id")
	private Long proprietarioId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "torre_bloco_id")
	private TorresBlocos torreBloco;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "apartamento_id")
	private Apartamentos apartamento;

	public Long getMoradorId() {
		return moradorId;
	}

	public void setMoradorId(Long moradorId) {
		this.moradorId = moradorId;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Boolean getPossuiVeiculo() {
		return possuiVeiculo;
	}

	public void setPossuiVeiculo(Boolean possuiVeiculo) {
		this.possuiVeiculo = possuiVeiculo;
	}

	public TipoMorador getTipoMorador() {
		return tipoMorador;
	}

	public void setTipoMorador(TipoMorador tipoMorador) {
		this.tipoMorador = tipoMorador;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getProprietarioId() {
		return proprietarioId;
	}

	public void setProprietarioId(Long proprietarioId) {
		this.proprietarioId = proprietarioId;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	public TorresBlocos getTorreBloco() {
		return torreBloco;
	}

	public void setTorreBloco(TorresBlocos torreBloco) {
		this.torreBloco = torreBloco;
	}

	public Apartamentos getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamentos apartamento) {
		this.apartamento = apartamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moradorId == null) ? 0 : moradorId.hashCode());
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
		Moradores other = (Moradores) obj;
		if (moradorId == null) {
			if (other.moradorId != null)
				return false;
		} else if (!moradorId.equals(other.moradorId))
			return false;
		return true;
	}

}
