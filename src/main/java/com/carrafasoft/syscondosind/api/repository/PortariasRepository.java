package com.carrafasoft.syscondosind.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Portaria;
import com.carrafasoft.syscondosind.api.repository.portaria.PortariaRepositoryQuery;

@Repository
public interface PortariasRepository extends JpaRepository<Portaria, Long>, PortariaRepositoryQuery{
	
	
	@Query(nativeQuery = true, value = "select * from portaria "
			+ "where nome_visitante like %:nome% ")
	public List<Portaria> buscaPorNome(String nome);
	
	
	@Query(nativeQuery = true, value = "select * from portaria "
			+ "where data_entrada between :dataEntradaDe and :dataEntradaAte ")
	public List<Portaria> buscaEntreDatas(LocalDateTime dataEntradaDe, LocalDateTime dataEntradaAte);
	

}
