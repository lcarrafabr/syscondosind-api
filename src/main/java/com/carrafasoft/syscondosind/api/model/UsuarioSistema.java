package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario_sistema")
public class UsuarioSistema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long usuarioSistemaId;
	
	@NotNull
	@Size(min = 6, max = 150)
	@Column(name = "nome_usuario", length = 150)
	private String nomeUsuario;
	
	@NotNull
	@Size(min = 8)
	private String password;
	
	@NotNull
	private Boolean Status;
	
	@Column(name = "data_cadastro", updatable = false)
	private LocalDateTime dataCadastro;
	
	@Column(name = "data_ultima_alteracao")
	private LocalDateTime dataUltimaAlteracao;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	public Long getUsuarioSistemaId() {
		return usuarioSistemaId;
	}

	public void setUsuarioSistemaId(Long usuarioSistemaId) {
		this.usuarioSistemaId = usuarioSistemaId;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioSistemaId == null) ? 0 : usuarioSistemaId.hashCode());
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
		UsuarioSistema other = (UsuarioSistema) obj;
		if (usuarioSistemaId == null) {
			if (other.usuarioSistemaId != null)
				return false;
		} else if (!usuarioSistemaId.equals(other.usuarioSistemaId))
			return false;
		return true;
	}
	
	
	@PrePersist
	public void aoCadastrarUsuario() {
		
		dataCadastro = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}
	
	@PreUpdate
	public void aoAtualizarUsuario() {
		
		dataUltimaAlteracao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

}
