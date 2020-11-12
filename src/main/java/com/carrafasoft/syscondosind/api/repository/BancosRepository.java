package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Bancos;

@Repository
public interface BancosRepository extends JpaRepository<Bancos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from bancos where status = 1 ")
	public List<Bancos> buscarBancosAtivos();

}
