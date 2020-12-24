package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDate;

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
@Table(name = "retirada_de_produtos")
public class RetiradaDeProdutos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "retirada_produto_id")
	private Long retiradaProdutoId;

	@NotNull
	@Column(name = "data_retirada")
	private LocalDate dataRetirada;

	@NotNull
	private Double quantidade;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties({ "condominio", "fornecedor", "unidadeMedida" })
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produtos produto;

	public Long getRetiradaProdutoId() {
		return retiradaProdutoId;
	}

	public void setRetiradaProdutoId(Long retiradaProdutoId) {
		this.retiradaProdutoId = retiradaProdutoId;
	}

	public LocalDate getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDate dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((retiradaProdutoId == null) ? 0 : retiradaProdutoId.hashCode());
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
		RetiradaDeProdutos other = (RetiradaDeProdutos) obj;
		if (retiradaProdutoId == null) {
			if (other.retiradaProdutoId != null)
				return false;
		} else if (!retiradaProdutoId.equals(other.retiradaProdutoId))
			return false;
		return true;
	}

}
