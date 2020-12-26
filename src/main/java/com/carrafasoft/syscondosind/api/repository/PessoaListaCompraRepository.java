package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.PessoaListaCompras;

@Repository
public interface PessoaListaCompraRepository extends JpaRepository<PessoaListaCompras, Long>{
	
	@Query(nativeQuery = true,
			value = "select pl.*, p.nome_pessoa "
					+ "from pessoa_lista_compra pl "
					+ "inner join pessoas p on p.pessoa_id = pl.pessoa_id "
					+ "where p.pessoa_id = :pessoaID ")
	PessoaListaCompras buscaPorCodigoPessoa(Long pessoaID);

}
