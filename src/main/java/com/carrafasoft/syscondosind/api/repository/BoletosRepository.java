package com.carrafasoft.syscondosind.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Boletos;

@Repository
public interface BoletosRepository extends JpaRepository<Boletos, Long>{
	
	@Query(nativeQuery = true,
			value = "select * from boletos where lancamento_gerado <> 1")
	List<Boletos> listarTodosBoletosSemLancamentoGerado();
	
	
	@Query(nativeQuery = true,
			value = "select * from boletos "
					+ "where lancamento_gerado <> 1 "
					+ "and data_documento between :dataIni and :dataFim ")
	List<Boletos> listarBoletosSemLancamentoPorData(LocalDate dataIni, LocalDate dataFim);

}
