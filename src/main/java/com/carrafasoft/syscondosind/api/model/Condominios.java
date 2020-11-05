package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;





@Entity
@Table(name = "condominios")
public class Condominios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "condominio_id")
	private Long condominioId;

	@NotNull
	@Size(min = 3, max = 255)
	@Column(name = "nome_condominio", length = 255)
	private String nomeCondomonio;

	@NotNull
	@Column(name = "cnpj_cpf", length = 20)
	private String cnpjCpf;

	@NotNull
	@Column(length = 10)
	private String formato;

	@NotNull
	@Column(name = "tipo_condominio", length = 20)
	private String tipoCOndominio;

	@NotNull
	private Boolean status;

	@NotNull
	@Column(length = 150)
	private String email;

	@Embedded
	private EnderecoCondominios enderecoCondominio;
	

	public Long getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(Long condominioId) {
		this.condominioId = condominioId;
	}

	public String getNomeCondomonio() {
		return nomeCondomonio;
	}

	public void setNomeCondomonio(String nomeCondomonio) {
		this.nomeCondomonio = nomeCondomonio;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getTipoCOndominio() {
		return tipoCOndominio;
	}

	public void setTipoCOndominio(String tipoCOndominio) {
		this.tipoCOndominio = tipoCOndominio;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoCondominios getEnderecoCondominio() {
		return enderecoCondominio;
	}

	public void setEnderecoCondominio(EnderecoCondominios enderecoCondominio) {
		this.enderecoCondominio = enderecoCondominio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condominioId == null) ? 0 : condominioId.hashCode());
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
		Condominios other = (Condominios) obj;
		if (condominioId == null) {
			if (other.condominioId != null)
				return false;
		} else if (!condominioId.equals(other.condominioId))
			return false;
		return true;
	}

}
