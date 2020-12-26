package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.model.interfaces.SolicitacaoCompraInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pessoa_lista_compra")
public class PessoaListaCompras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_lista_compra_id")
	private Long pessoaListaCompraID;

	@NotNull
	@Column(name = "permite_aprovar")
	private Boolean permiteAprovar;

	@NotNull
	@Column(name = "permite_criar_solicitacao")
	private Boolean permiteCriarSolicitacao;

	@Column(name = "data_cadastro", insertable = true, updatable = false)
	private LocalDate dataCadastro;

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

	public Long getPessoaListaCompraID() {
		return pessoaListaCompraID;
	}

	public void setPessoaListaCompraID(Long pessoaListaCompraID) {
		this.pessoaListaCompraID = pessoaListaCompraID;
	}

	public Boolean getPermiteAprovar() {
		return permiteAprovar;
	}

	public void setPermiteAprovar(Boolean permiteAprovar) {
		this.permiteAprovar = permiteAprovar;
	}

	public Boolean getPermiteCriarSolicitacao() {
		return permiteCriarSolicitacao;
	}

	public void setPermiteCriarSolicitacao(Boolean permiteCriarSolicitacao) {
		this.permiteCriarSolicitacao = permiteCriarSolicitacao;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoaListaCompraID == null) ? 0 : pessoaListaCompraID.hashCode());
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
		PessoaListaCompras other = (PessoaListaCompras) obj;
		if (pessoaListaCompraID == null) {
			if (other.pessoaListaCompraID != null)
				return false;
		} else if (!pessoaListaCompraID.equals(other.pessoaListaCompraID))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		dataCadastro = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
	}

}
