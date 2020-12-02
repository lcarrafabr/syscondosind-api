package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "beneficiario")
public class Beneficiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beneficiario_id")
	private Long beneficiarioId;

	@NotNull
	@Column(name = "nome_beneficiario", length = 200)
	private String nomeBeneficiario;

	@NotNull
	@Column(name = "codigo_beneficiario", length = 45)
	private String codigoBeneficiario;

	@NotNull
	@Column(name = "digito_beneficiario", length = 5)
	private String digitoBeneficiario;

	@Column(name = "numero_convenio")
	private String numeroConvenio;

	@NotNull
	@Column(name = "cpf_cnpj", length = 20)
	private String cpfCnpj;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Enderecos enderecos;

	public Long getBeneficiarioId() {
		return beneficiarioId;
	}

	public void setBeneficiarioId(Long beneficiarioId) {
		this.beneficiarioId = beneficiarioId;
	}

	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}

	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}

	public String getCodigoBeneficiario() {
		return codigoBeneficiario;
	}

	public void setCodigoBeneficiario(String codigoBeneficiario) {
		this.codigoBeneficiario = codigoBeneficiario;
	}

	public String getDigitoBeneficiario() {
		return digitoBeneficiario;
	}

	public void setDigitoBeneficiario(String digitoBeneficiario) {
		this.digitoBeneficiario = digitoBeneficiario;
	}

	public String getNumeroConvenio() {
		return numeroConvenio;
	}

	public void setNumeroConvenio(String numeroConvenio) {
		this.numeroConvenio = numeroConvenio;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Enderecos getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Enderecos enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficiarioId == null) ? 0 : beneficiarioId.hashCode());
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
		Beneficiario other = (Beneficiario) obj;
		if (beneficiarioId == null) {
			if (other.beneficiarioId != null)
				return false;
		} else if (!beneficiarioId.equals(other.beneficiarioId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		toUppercase();
	}

	@PreUpdate
	public void aoAtualizar() {

		toUppercase();
	}

	private void toUppercase() {

		nomeBeneficiario = nomeBeneficiario.trim().toUpperCase();

	}

}
