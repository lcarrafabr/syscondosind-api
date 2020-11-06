package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario_permissao")
public class UsuarioPermissao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_permissao")
	private Long usuarioPermissao;

	@NotNull
	@Column(name = "usuario_sistema")
	private Long usuarioSistema;

	@NotNull
	@Column(name = "permissao_id")
	private Long permissaoId;

	public Long getUsuarioPermissao() {
		return usuarioPermissao;
	}

	public void setUsuarioPermissao(Long usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}

	public Long getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(Long usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public Long getPermissaoId() {
		return permissaoId;
	}

	public void setPermissaoId(Long permissaoId) {
		this.permissaoId = permissaoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permissaoId == null) ? 0 : permissaoId.hashCode());
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
		UsuarioPermissao other = (UsuarioPermissao) obj;
		if (permissaoId == null) {
			if (other.permissaoId != null)
				return false;
		} else if (!permissaoId.equals(other.permissaoId))
			return false;
		return true;
	}

}
