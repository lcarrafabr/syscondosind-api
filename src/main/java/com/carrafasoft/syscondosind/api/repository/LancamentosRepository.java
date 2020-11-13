package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Lancamentos;

@Repository
public interface LancamentosRepository extends JpaRepository<Lancamentos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from lancamentos where chave_pesquisa = :chave ")
	public List<Lancamentos> buscarPorchave(String chave);
	


}
