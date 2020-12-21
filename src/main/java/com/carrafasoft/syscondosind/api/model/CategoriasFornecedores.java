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
@Table(name = "categorias_fornecedores")
public class CategoriasFornecedores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_fornecedor_id")
	private Long categoriaFornecedorId;

	@NotNull
	@Column(name = "nome_categoria", length = 150)
	private String nomeCategoria;

	@NotNull
	@Column(name = "status_ativo", length = 10)
	private Boolean statusAtivo;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getCategoriaFornecedorId() {
		return categoriaFornecedorId;
	}

	public void setCategoriaFornecedorId(Long categoriaFornecedorId) {
		this.categoriaFornecedorId = categoriaFornecedorId;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public Boolean getStatusAtivo() {
		return statusAtivo;
	}

	public void setStatusAtivo(Boolean statusAtivo) {
		this.statusAtivo = statusAtivo;
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
		result = prime * result + ((categoriaFornecedorId == null) ? 0 : categoriaFornecedorId.hashCode());
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
		CategoriasFornecedores other = (CategoriasFornecedores) obj;
		if (categoriaFornecedorId == null) {
			if (other.categoriaFornecedorId != null)
				return false;
		} else if (!categoriaFornecedorId.equals(other.categoriaFornecedorId))
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

		nomeCategoria = nomeCategoria.trim().toUpperCase();
	}

}
