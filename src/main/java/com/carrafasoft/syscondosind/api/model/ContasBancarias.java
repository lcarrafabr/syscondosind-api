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

import com.carrafasoft.syscondosind.api.enums.TipoConta;
import com.carrafasoft.syscondosind.api.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "contas_bancarias")
public class ContasBancarias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conta_bancaria_id")
	private Long contaBancariaId;

	@NotNull
	@Size(min = 3, max = 150)
	@Column(name = "nome_conta", length = 150)
	private String nomeConta;

	@NotNull
	@Column(name = "numero_conta", length = 20)
	private String numeroConta;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta", length = 45)
	private TipoConta tipoConta;

	@NotNull
	@Column(name = "digito_conta", length = 5)
	private String digitoConta;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta_pessoa", length = 20)
	private TipoPessoa tipocontaPessoa;

	@NotNull
	private Boolean status;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getContaBancariaId() {
		return contaBancariaId;
	}

	public void setContaBancariaId(Long contaBancariaId) {
		this.contaBancariaId = contaBancariaId;
	}

	public String getNomeConta() {
		return nomeConta;
	}

	public void setNomeConta(String nomeConta) {
		this.nomeConta = nomeConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public TipoPessoa getTipocontaPessoa() {
		return tipocontaPessoa;
	}

	public void setTipocontaPessoa(TipoPessoa tipocontaPessoa) {
		this.tipocontaPessoa = tipocontaPessoa;
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

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contaBancariaId == null) ? 0 : contaBancariaId.hashCode());
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
		ContasBancarias other = (ContasBancarias) obj;
		if (contaBancariaId == null) {
			if (other.contaBancariaId != null)
				return false;
		} else if (!contaBancariaId.equals(other.contaBancariaId))
			return false;
		return true;
	}

}
