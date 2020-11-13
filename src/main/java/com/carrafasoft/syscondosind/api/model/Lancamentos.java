package com.carrafasoft.syscondosind.api.model;

import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.FormaPagamento;
import com.carrafasoft.syscondosind.api.enums.StatusSituacao;
import com.carrafasoft.syscondosind.api.enums.TipoNatureza;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lancamentos")
public class Lancamentos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lancamento_id")
	private Long lancamentoId;

	@NotNull
	@Column(name = "tipo_natureza", length = 20)
	@Enumerated(EnumType.STRING)
	private TipoNatureza tipoNatureza;

	@NotNull
	private BigDecimal valor;

	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(length = 100)
	private String descricao;

	@NotNull
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private StatusSituacao situacao;

	@NotNull
	private Boolean parcelado;

	@NotNull
	private Integer quantidadeParcelas;

	@NotNull
	@Column(name = "numero_parcela")
	private Integer numeroParcela;

	@NotNull
	@Column(name = "forma_pagamento", length = 20)
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "conta_bancaria_id")
	private ContasBancarias contaBancaria;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "centro_custo_id")
	private CentroDeCustos centroCusto;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_conta_id")
	private CategoriasContaPR categoriaConta;

	public Long getLancamentoId() {
		return lancamentoId;
	}

	public void setLancamentoId(Long lancamentoId) {
		this.lancamentoId = lancamentoId;
	}

	public TipoNatureza getTipoNatureza() {
		return tipoNatureza;
	}

	public void setTipoNatureza(TipoNatureza tipoNatureza) {
		this.tipoNatureza = tipoNatureza;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusSituacao situacao) {
		this.situacao = situacao;
	}

	public Boolean getParcelado() {
		return parcelado;
	}

	public void setParcelado(Boolean parcelado) {
		this.parcelado = parcelado;
	}

	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public ContasBancarias getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContasBancarias contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public CentroDeCustos getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroDeCustos centroCusto) {
		this.centroCusto = centroCusto;
	}

	public CategoriasContaPR getCategoriaConta() {
		return categoriaConta;
	}

	public void setCategoriaConta(CategoriasContaPR categoriaConta) {
		this.categoriaConta = categoriaConta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lancamentoId == null) ? 0 : lancamentoId.hashCode());
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
		Lancamentos other = (Lancamentos) obj;
		if (lancamentoId == null) {
			if (other.lancamentoId != null)
				return false;
		} else if (!lancamentoId.equals(other.lancamentoId))
			return false;
		return true;
	}

}
