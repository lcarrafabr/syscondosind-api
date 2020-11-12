package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categorias_conta_pr")
public class CategoriasContaPR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_conta_id")
	private Long categoriaContaId;

	@NotNull
	@Column(name = "nome_categoria_conta", length = 80)
	private String nomeCategoriaConta;

	@NotNull
	private Boolean status;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	public Long getCategoriaContaId() {
		return categoriaContaId;
	}

	public void setCategoriaContaId(Long categoriaContaId) {
		this.categoriaContaId = categoriaContaId;
	}

	public String getNomeCategoriaConta() {
		return nomeCategoriaConta;
	}

	public void setNomeCategoriaConta(String nomeCategoriaConta) {
		this.nomeCategoriaConta = nomeCategoriaConta;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaContaId == null) ? 0 : categoriaContaId.hashCode());
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
		CategoriasContaPR other = (CategoriasContaPR) obj;
		if (categoriaContaId == null) {
			if (other.categoriaContaId != null)
				return false;
		} else if (!categoriaContaId.equals(other.categoriaContaId))
			return false;
		return true;
	}

}
