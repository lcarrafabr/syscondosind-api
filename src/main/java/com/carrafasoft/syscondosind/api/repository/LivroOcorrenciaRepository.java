package com.carrafasoft.syscondosind.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.LivroOcorrencia;

@Repository
public interface LivroOcorrenciaRepository extends JpaRepository<LivroOcorrencia, Long>{
	
	@Query(nativeQuery = true, value = "select * from livro_ocorrencia "
			+ "where data_ocorrencia between :dataOcorrenciaDe and :dataOcorrenciaAte ")
	public List<LivroOcorrencia> buscarEntreDataAbertura(LocalDateTime dataOcorrenciaDe, LocalDateTime dataOcorrenciaAte);
	
	
	@Query(nativeQuery = true, value = "select * from livro_ocorrencia "
			+ "where status_ocorrencia = :status ")
	public List<LivroOcorrencia> buscaPorStatusOcorrencia(String status);
	
	
	@Query(nativeQuery = true, value = "select count(status_ocorrencia) as qtd "
			+ "from livro_ocorrencia "
			+ "where status_ocorrencia = :status ")
	public Long buscaQtdPorStatus(String status);

}
