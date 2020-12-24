package com.carrafasoft.syscondosind.api.model.apoio;

import java.math.BigInteger;
import java.time.LocalDate;

public class ControleEstoqueModelApoio {

	private BigInteger produtoId;

	private String nomeProduto;

	private String codigoDeBarras;

	private LocalDate dataDecompra;

	private Double qtdEmEstoque;

	private Double qtdComprada;

	public BigInteger getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(BigInteger produtoId) {
		this.produtoId = produtoId;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public LocalDate getDataDecompra() {
		return dataDecompra;
	}

	public void setDataDecompra(LocalDate dataDecompra) {
		this.dataDecompra = dataDecompra;
	}

	public Double getQtdEmEstoque() {
		return qtdEmEstoque;
	}

	public void setQtdEmEstoque(Double qtdEmEstoque) {
		this.qtdEmEstoque = qtdEmEstoque;
	}

	public Double getQtdComprada() {
		return qtdComprada;
	}

	public void setQtdComprada(Double qtdComprada) {
		this.qtdComprada = qtdComprada;
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
		ControleEstoqueModelApoio other = (ControleEstoqueModelApoio) obj;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

}
