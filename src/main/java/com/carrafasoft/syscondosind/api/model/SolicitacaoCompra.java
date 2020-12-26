package com.carrafasoft.syscondosind.api.model;

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

import com.carrafasoft.syscondosind.api.enums.StatusSolicitacaoCOmpraEnum;
import com.carrafasoft.syscondosind.api.model.interfaces.SolicitacaoCompraInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "solicitacao_compra")
public class SolicitacaoCompra implements SolicitacaoCompraInterface{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "solicitacao_compra_id")
	private Long solicitacaoCompraID;

	@Column(name = "solicitacao_compra_codigo")
	private Long solicitacaoCompraCodigo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private StatusSolicitacaoCOmpraEnum statusSolicitacaoCompra;

	@Column(name = "data_abertura", insertable = true, updatable = false)
	private LocalDate dataAbertura;

	@Column(name = "data_fechamento", insertable = false, updatable = true)
	private LocalDate dataFechamento;

	@Column(name = "pessoa_aprovador")
	private Long pessoaAprovador;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "pessoa_lista_compra_id")
	private PessoaListaCompras pessoaListaCompra;

	public Long getSolicitacaoCompraID() {
		return solicitacaoCompraID;
	}

	public void setSolicitacaoCompraID(Long solicitacaoCompraID) {
		this.solicitacaoCompraID = solicitacaoCompraID;
	}

	public Long getSolicitacaoCompraCodigo() {
		return solicitacaoCompraCodigo;
	}

	public void setSolicitacaoCompraCodigo(Long solicitacaoCompraCodigo) {
		this.solicitacaoCompraCodigo = solicitacaoCompraCodigo;
	}

	public StatusSolicitacaoCOmpraEnum getStatusSolicitacaoCompra() {
		return statusSolicitacaoCompra;
	}

	public void setStatusSolicitacaoCompra(StatusSolicitacaoCOmpraEnum statusSolicitacaoCompra) {
		this.statusSolicitacaoCompra = statusSolicitacaoCompra;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Long getPessoaAprovador() {
		return pessoaAprovador;
	}

	public void setPessoaAprovador(Long pessoaAprovador) {
		this.pessoaAprovador = pessoaAprovador;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public PessoaListaCompras getPessoaListaCompra() {
		return pessoaListaCompra;
	}

	public void setPessoaListaCompra(PessoaListaCompras pessoaListaCompra) {
		this.pessoaListaCompra = pessoaListaCompra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solicitacaoCompraID == null) ? 0 : solicitacaoCompraID.hashCode());
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
		SolicitacaoCompra other = (SolicitacaoCompra) obj;
		if (solicitacaoCompraID == null) {
			if (other.solicitacaoCompraID != null)
				return false;
		} else if (!solicitacaoCompraID.equals(other.solicitacaoCompraID))
			return false;
		return true;
	}

	@PrePersist
	private void aoCadastrar() {

		dataAbertura = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
	}

	@Override
	public void gerarCodigoSolicitacao(Long idAtual) {
		
		solicitacaoCompraCodigo = idAtual + 1;
		
	}

	@Override
	public void cancelarSolicitacao() {
		
		statusSolicitacaoCompra = StatusSolicitacaoCOmpraEnum.CANCELADO;
		dataFechamento = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
		
	}


}
