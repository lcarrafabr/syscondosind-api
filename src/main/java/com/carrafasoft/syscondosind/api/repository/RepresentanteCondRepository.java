package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.RepresentantesCondominio;

@Repository
public interface RepresentanteCondRepository extends JpaRepository<RepresentantesCondominio, Long>{
	
	@Query(nativeQuery = true, value = "select r.* from representantes_condominio r "
			+ "where r.data_fim_mandato is null ")
	List<RepresentantesCondominio> findRepresentanteAtivo();
	
	@Query(nativeQuery = true, value = "select r.* from representantes_condominio r "
			+ "where r.data_fim_mandato is not null ")
	List<RepresentantesCondominio> findRepresentantesInativos();

}
