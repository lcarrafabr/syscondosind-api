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
@Table(name = "fornecedores")
public class Fornecedores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fornecedor_id")
	private Long fornecedorId;

	@NotNull
	@Column(name = "nome_fornecdor", length = 200)
	private String nomeFornecedor;

	@Column(name = "razao_social", length = 200)
	private String razaoSocial;

	@Column(name = "cpf_cnpj", length = 20)
	private String cpfCNPJ;

	@NotNull
	private Boolean status;

	@Column(name = "nome_contato", length = 200)
	private String nomeContato;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;


	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominios")
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Enderecos endereco;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "categoria_fornecedor_id")
	private CategoriasFornecedores categoriaFornecedor;

	public Long getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCpfCNPJ() {
		return cpfCNPJ;
	}

	public void setCpfCNPJ(String cpfCNPJ) {
		this.cpfCNPJ = cpfCNPJ;
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

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
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

	public Enderecos getEndereco() {
		return endereco;
	}

	public void setEndereco(Enderecos endereco) {
		this.endereco = endereco;
	}

	public CategoriasFornecedores getCategoriaFornecedor() {
		return categoriaFornecedor;
	}

	public void setCategoriaFornecedor(CategoriasFornecedores categoriaFornecedor) {
		this.categoriaFornecedor = categoriaFornecedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fornecedorId == null) ? 0 : fornecedorId.hashCode());
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
		Fornecedores other = (Fornecedores) obj;
		if (fornecedorId == null) {
			if (other.fornecedorId != null)
				return false;
		} else if (!fornecedorId.equals(other.fornecedorId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		toUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {

		toUpperCase();
	}

	private void toUpperCase() {

		if (nomeFornecedor != null && !nomeFornecedor.isEmpty()) {

			nomeFornecedor = nomeFornecedor.trim().toUpperCase();
		}

		if (razaoSocial != null && !razaoSocial.isEmpty()) {

			razaoSocial = razaoSocial.trim().toUpperCase();
		}

		if (nomeContato != null && !nomeContato.isEmpty()) {
			nomeContato = nomeContato.trim().toUpperCase();
		}
	}

}
