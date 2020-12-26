package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.SolicitacaoCompra;

@Repository
public interface SolicitacaoCompraRepository extends JpaRepository<SolicitacaoCompra, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select if( max(solicitacao_compra_codigo) is null , 0,  max(solicitacao_compra_codigo)) as \"ultimo_id\" "
					+ "from solicitacao_compra "
					+ "where condominio_id = :condominioID ")
		Long ultimoId(Long condominioID);

}
