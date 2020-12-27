package com.carrafasoft.syscondosind.api.model;

import java.math.BigDecimal;

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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.StatusAprovacaoEnum;
import com.carrafasoft.syscondosind.api.model.interfaces.SolicitacaoCompraItensInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "solicitacao_compra_itens")
public class SolicitacaoCompraItens implements SolicitacaoCompraItensInterface {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "solicitacao_compra_itens_id")
	private Long solicitacaoCompraItemID;

	@NotNull
	@Size(min = 2, max = 150)
	@Column(name = "nome_produto", length = 150)
	private String nomeProduto;

	@NotNull
	private Double quantidade;

	@NotNull
	@Column(name = "preco_cotacao")
	private BigDecimal precoCotacao;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_aprovacao")
	private StatusAprovacaoEnum statusAprovacaoEnum;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "solicitacao_compra_id")
	private SolicitacaoCompra solicitacaoCompra;

	public Long getSolicitacaoCompraItemID() {
		return solicitacaoCompraItemID;
	}

	public void setSolicitacaoCompraItemID(Long solicitacaoCompraItemID) {
		this.solicitacaoCompraItemID = solicitacaoCompraItemID;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCotacao() {
		return precoCotacao;
	}

	public void setPrecoCotacao(BigDecimal precoCotacao) {
		this.precoCotacao = precoCotacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusAprovacaoEnum getStatusAprovacaoEnum() {
		return statusAprovacaoEnum;
	}

	public void setStatusAprovacaoEnum(StatusAprovacaoEnum statusAprovacaoEnum) {
		this.statusAprovacaoEnum = statusAprovacaoEnum;
	}

	public SolicitacaoCompra getSolicitacaoCompra() {
		return solicitacaoCompra;
	}

	public void setSolicitacaoCompra(SolicitacaoCompra solicitacaoCompra) {
		this.solicitacaoCompra = solicitacaoCompra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solicitacaoCompraItemID == null) ? 0 : solicitacaoCompraItemID.hashCode());
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
		SolicitacaoCompraItens other = (SolicitacaoCompraItens) obj;
		if (solicitacaoCompraItemID == null) {
			if (other.solicitacaoCompraItemID != null)
				return false;
		} else if (!solicitacaoCompraItemID.equals(other.solicitacaoCompraItemID))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {
		statusAprovacaoEnum = StatusAprovacaoEnum.AGUARDANDO;
		calcularPrecoTotal();
		nomeProduto = nomeProduto.trim().toUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {
		calcularPrecoTotal();
		nomeProduto = nomeProduto.trim().toUpperCase();
	}

	@Override
	public void aprovarItemSolicitacaoo() {

		statusAprovacaoEnum = StatusAprovacaoEnum.APROVADO;
	}

	@Override
	public void cancelarItemSolicitacao() {

		statusAprovacaoEnum = StatusAprovacaoEnum.NAO_APROVADO;
	}

	private void calcularPrecoTotal() {

		if (precoCotacao.equals(null)) {
			precoCotacao = BigDecimal.ZERO;
		}
		if (quantidade.equals(null)) {
			quantidade = 0.0;
		}

		valorTotal = precoCotacao.multiply(new BigDecimal(quantidade));
	}

}
