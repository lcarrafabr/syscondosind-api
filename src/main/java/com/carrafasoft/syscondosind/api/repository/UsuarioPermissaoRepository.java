package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.syscondosind.api.model.UsuarioPermissao;

@Repository
public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, Long>{
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from usuario_permissao "
			+ "where usuario_sistema = ?1 ")
	public void bloquearTodosOsAcessos(Long codigoUsuario);

}
