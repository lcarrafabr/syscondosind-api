package com.carrafasoft.syscondosind.api.model;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pessoas")
public class Pessoas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_id")
	private Long pessoaId;

	@NotNull
	@Size(min = 3)
	@Column(name = "nome_pessoa", length = 200)
	private String nomePessoa;

	@NotNull
	private Boolean status;

	@Column(name = "data_cadastro", updatable = false)
	private LocalDate dataCadastro;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
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
		result = prime * result + ((pessoaId == null) ? 0 : pessoaId.hashCode());
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
		Pessoas other = (Pessoas) obj;
		if (pessoaId == null) {
			if (other.pessoaId != null)
				return false;
		} else if (!pessoaId.equals(other.pessoaId))
			return false;
		return true;
	}
	
	
	@PrePersist
	public void aoCadastrarPessoa() {
		
		dataCadastro = LocalDate.now();
	}

}
