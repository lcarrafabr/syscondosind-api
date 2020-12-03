package com.carrafasoft.syscondosind.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.SituacaoBoleto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "boletos")
public class Boletos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "boleto_id")
	private Long boletoId;

	@Column(name = "data_documento", updatable = false)
	private LocalDate dataDocumento;

	@Column(name = "data_processamento")
	private LocalDate dataProcessamento;

	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@NotNull
	private BigDecimal valor;

	@Column(name = "numero_documento")
	private Long numeroDocumento;

	@Column(name = "nosso_numero")
	private Long nossoNumero;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_boleto")
	private SituacaoBoleto situacaoBoleto;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "modelo_boleto_id")
	private ModeloBoletos modeloBoleto;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "")
	private Moradores morador;

	public Long getBoletoId() {
		return boletoId;
	}

	public void setBoletoId(Long boletoId) {
		this.boletoId = boletoId;
	}

	public LocalDate getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(LocalDate dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public LocalDate getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(LocalDate dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getNumeroDocumento() {
		return numeroDocumento;
	}

	public SituacaoBoleto getSituacaoBoleto() {
		return situacaoBoleto;
	}

	public void setSituacaoBoleto(SituacaoBoleto situacaoBoleto) {
		this.situacaoBoleto = situacaoBoleto;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public Long getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(Long nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public ModeloBoletos getModeloBoleto() {
		return modeloBoleto;
	}

	public void setModeloBoleto(ModeloBoletos modeloBoleto) {
		this.modeloBoleto = modeloBoleto;
	}

	public Moradores getMorador() {
		return morador;
	}

	public void setMorador(Moradores morador) {
		this.morador = morador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boletoId == null) ? 0 : boletoId.hashCode());
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
		Boletos other = (Boletos) obj;
		if (boletoId == null) {
			if (other.boletoId != null)
				return false;
		} else if (!boletoId.equals(other.boletoId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		dataDocumento = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
		situacaoBoleto = SituacaoBoleto.EM_ABERTO;
	}

}
