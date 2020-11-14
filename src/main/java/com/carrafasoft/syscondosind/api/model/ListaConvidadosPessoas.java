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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "lista_convidado_pessoas")
public class ListaConvidadosPessoas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lista_convidado_pessoa_id")
	private Long listaConvidadoPessoaId;

	@NotNull
	@Size(min = 3, max = 200)
	@Column(name = "nome_pessoa", length = 200)
	private String nomePessoa;

	@NotNull
	private Boolean convite;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "lista_id")
	private ListaConvidados listaConvidados;

	public Long getListaConvidadoPessoaId() {
		return listaConvidadoPessoaId;
	}

	public void setListaConvidadoPessoaId(Long listaConvidadoPessoaId) {
		this.listaConvidadoPessoaId = listaConvidadoPessoaId;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Boolean getConvite() {
		return convite;
	}

	public void setConvite(Boolean convite) {
		this.convite = convite;
	}

	public ListaConvidados getListaConvidados() {
		return listaConvidados;
	}

	public void setListaConvidados(ListaConvidados listaConvidados) {
		this.listaConvidados = listaConvidados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaConvidadoPessoaId == null) ? 0 : listaConvidadoPessoaId.hashCode());
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
		ListaConvidadosPessoas other = (ListaConvidadosPessoas) obj;
		if (listaConvidadoPessoaId == null) {
			if (other.listaConvidadoPessoaId != null)
				return false;
		} else if (!listaConvidadoPessoaId.equals(other.listaConvidadoPessoaId))
			return false;
		return true;
	}
	
	@PrePersist
	public void aoCadastrar() {
		
		nomePessoa = nomePessoa.toUpperCase().trim();
	}
	
	@PreUpdate
	public void aoAtualizar() {
		
		nomePessoa = nomePessoa.toUpperCase().trim();
	}

}
