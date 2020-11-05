package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Pessoas;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoas, Long>{
	
	@Query(nativeQuery = true, value = "select * from pessoas where status = 1")
	List<Pessoas> FindByStatusAtivo();

}
