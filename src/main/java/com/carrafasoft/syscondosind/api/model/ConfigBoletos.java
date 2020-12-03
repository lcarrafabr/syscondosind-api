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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "config_boletos")
public class ConfigBoletos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_boleto_id")
	private Long configBoletoId;

	@NotNull
	@Column(name = "nosso_numero_inicio")
	private Long nossoNumeroInicio;

	@NotNull
	@Column(name = "nosso_numero_atual")
	private Long nossoNumeroAtual;

	@NotNull
	@Column(name = "nosso_numero_final")
	private Long nossoNumeroFinal;

	@Column(name = "digito_nosso_numero")
	private String digitoNossoNumero;

	@NotNull
	@Column(name = "ultimo_numero_doc")
	private Long ultimoNumeroDoc;

	@NotNull
	@Column(name = "ultimo_numero_remessa")
	private Long ultimoNumeroRemessa;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getConfigBoletoId() {
		return configBoletoId;
	}

	public void setConfigBoletoId(Long configBoletoId) {
		this.configBoletoId = configBoletoId;
	}

	public Long getNossoNumeroInicio() {
		return nossoNumeroInicio;
	}

	public void setNossoNumeroInicio(Long nossoNumeroInicio) {
		this.nossoNumeroInicio = nossoNumeroInicio;
	}

	public Long getNossoNumeroAtual() {
		return nossoNumeroAtual;
	}

	public void setNossoNumeroAtual(Long nossoNumeroAtual) {
		this.nossoNumeroAtual = nossoNumeroAtual;
	}

	public Long getNossoNumeroFinal() {
		return nossoNumeroFinal;
	}

	public void setNossoNumeroFinal(Long nossoNumeroFinal) {
		this.nossoNumeroFinal = nossoNumeroFinal;
	}

	public String getDigitoNossoNumero() {
		return digitoNossoNumero;
	}

	public void setDigitoNossoNumero(String digitoNossoNumero) {
		this.digitoNossoNumero = digitoNossoNumero;
	}

	public Long getUltimoNumeroDoc() {
		return ultimoNumeroDoc;
	}

	public void setUltimoNumeroDoc(Long ultimoNumeroDoc) {
		this.ultimoNumeroDoc = ultimoNumeroDoc;
	}

	public Long getUltimoNumeroRemessa() {
		return ultimoNumeroRemessa;
	}

	public void setUltimoNumeroRemessa(Long ultimoNumeroRemessa) {
		this.ultimoNumeroRemessa = ultimoNumeroRemessa;
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
		result = prime * result + ((configBoletoId == null) ? 0 : configBoletoId.hashCode());
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
		ConfigBoletos other = (ConfigBoletos) obj;
		if (configBoletoId == null) {
			if (other.configBoletoId != null)
				return false;
		} else if (!configBoletoId.equals(other.configBoletoId))
			return false;
		return true;
	}

}
