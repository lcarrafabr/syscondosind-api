package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDateTime;

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
@Table(name = "lista_convidados")
public class ListaConvidados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lista_id")
	private Long listaId;

	@NotNull
	@Size(max = 150)
	@Column(name = "nome_lista", length = 150)
	private String nomeLista;

	@NotNull
	private LocalDateTime validadeAte;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@ManyToOne
	@JsonIgnoreProperties("enderecoCondominio")
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	public Long getListaId() {
		return listaId;
	}

	public void setListaId(Long listaId) {
		this.listaId = listaId;
	}

	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}

	public LocalDateTime getValidadeAte() {
		return validadeAte;
	}

	public void setValidadeAte(LocalDateTime validadeAte) {
		this.validadeAte = validadeAte;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ((listaId == null) ? 0 : listaId.hashCode());
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
		ListaConvidados other = (ListaConvidados) obj;
		if (listaId == null) {
			if (other.listaId != null)
				return false;
		} else if (!listaId.equals(other.listaId))
			return false;
		return true;
	}
	
	
	@PrePersist
	public void aoCadastrar() {
		
		nomeLista = nomeLista.toUpperCase();
	}
	
	@PreUpdate
	public void aoAtualizar() {
		
		nomeLista = nomeLista.toUpperCase();
	}

}
