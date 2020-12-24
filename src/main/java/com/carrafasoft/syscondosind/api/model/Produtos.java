package com.carrafasoft.syscondosind.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "produtos")
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long produtoId;

	@NotNull
	@Size(min = 2, max = 150)
	@Column(name = "nome_produto", length = 150)
	private String nomeProduto;

	@NotNull
	private Double quantidade;

	@NotNull
	@Column(name = "preco_de_compra")
	private BigDecimal precoCompra;

	@NotNull
	@Column(name = "quantidade_estoque_minimo")
	private Integer qtdEstoqueMinimo;

	@NotNull
	@Column(name = "avisar_estoque_minimo")
	private Boolean avisarEstoqueMinimo;

	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@NotNull
	@Column(name = "avisar_vencimento")
	private Boolean avisarVencimento;

	@NotNull
	@Column(name = "codigo_de_barras", length = 30)
	private String codigoDeBarras;

	@Column(name = "data_de_compra")
	private LocalDate dataCompra;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties({ "condominio", "pessoa", "endereco", "categoriaFornecedor" })
	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedores fornecedor;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "unidade_de_medida_id")
	private UnidadeDeMedidas unidadeMedida;

	@Transient
	private String valorTotal;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
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

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Integer getQtdEstoqueMinimo() {
		return qtdEstoqueMinimo;
	}

	public void setQtdEstoqueMinimo(Integer qtdEstoqueMinimo) {
		this.qtdEstoqueMinimo = qtdEstoqueMinimo;
	}

	public Boolean getAvisarEstoqueMinimo() {
		return avisarEstoqueMinimo;
	}

	public void setAvisarEstoqueMinimo(Boolean avisarEstoqueMinimo) {
		this.avisarEstoqueMinimo = avisarEstoqueMinimo;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Boolean getAvisarVencimento() {
		return avisarVencimento;
	}

	public void setAvisarVencimento(Boolean avisarVencimento) {
		this.avisarVencimento = avisarVencimento;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Fornecedores getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}

	public UnidadeDeMedidas getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeDeMedidas unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produtoId == null) ? 0 : produtoId.hashCode());
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
		Produtos other = (Produtos) obj;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		dataCompra = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
		toUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {
		toUpperCase();
	}

	private void toUpperCase() {

		nomeProduto = nomeProduto.trim().toUpperCase();
	}

}
