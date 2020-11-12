package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.CentroDeCustos;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroDeCustos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from centro_de_custos where status = 1 ")
	public List<CentroDeCustos> buscaCentroCustoAtivo();

}
