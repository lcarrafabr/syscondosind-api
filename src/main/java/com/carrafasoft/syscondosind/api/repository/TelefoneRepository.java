package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Telefones;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefones, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from telefones "
					+ "where condominio_id = :condominioId "
					+ "and pessoa_id = :pessoaId ")
	List<Telefones> findByPessoaByCondominio(Long pessoaId, Long condominioId);

}
