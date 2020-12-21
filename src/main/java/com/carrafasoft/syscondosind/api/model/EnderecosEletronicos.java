package com.carrafasoft.syscondosind.api.model;

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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.TipoEnderecoEletronicoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "enderecos_eletronicos")
public class EnderecosEletronicos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "end_eletronico_id")
	private Long endEletronicoId;

	@NotNull
	@Email
	@Size(max = 150)
	@Column(length = 150)
	private String email;

	@Column(length = 200)
	private String site;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoEnderecoEletronicoEnum tipoEndEletronico;

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

	public Long getEndEletronicoId() {
		return endEletronicoId;
	}

	public void setEndEletronicoId(Long endEletronicoId) {
		this.endEletronicoId = endEletronicoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public TipoEnderecoEletronicoEnum getTipoEndEletronico() {
		return tipoEndEletronico;
	}

	public void setTipoEndEletronico(TipoEnderecoEletronicoEnum tipoEndEletronico) {
		this.tipoEndEletronico = tipoEndEletronico;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endEletronicoId == null) ? 0 : endEletronicoId.hashCode());
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
		EnderecosEletronicos other = (EnderecosEletronicos) obj;
		if (endEletronicoId == null) {
			if (other.endEletronicoId != null)
				return false;
		} else if (!endEletronicoId.equals(other.endEletronicoId))
			return false;
		return true;
	}
	
	@PrePersist
	public void aoCadastrar() {
		
		lowUppercase();
	}
	
	@PreUpdate
	public void aoAtualizar() {
		
		lowUppercase();
	}
	
	private void lowUppercase() {
		
		
		if(email != null && !email.isEmpty()) {
			
			email = email.trim().toLowerCase();
		}
		
		if(site != null && !site.isEmpty()) {
			
			site = site.trim().toLowerCase();
		}
	}

}
