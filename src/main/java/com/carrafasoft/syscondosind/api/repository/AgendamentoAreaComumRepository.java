package com.carrafasoft.syscondosind.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.AgendamentoAreaComum;

@Repository
public interface AgendamentoAreaComumRepository extends JpaRepository<AgendamentoAreaComum, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select data_inicio_agendamento from agendamento_area_comum "
					+ "where  :dataInicio >= data_inicio_agendamento "
					+ "and  :dataInicio <= data_fim_agendamento "
					+ "and area_comum_id = :codigoAreaComum "
					+ "limit 1 ")
	public LocalDateTime buscaDataInicioLivre(LocalDateTime dataInicio, Long codigoAreaComum);
	
	
	@Query(nativeQuery = true,
			value = "select data_fim_agendamento from agendamento_area_comum "
					+ "where  :dataFim >= data_inicio_agendamento "
					+ "and  :dataFim <= data_fim_agendamento "
					+ "and area_comum_id = :codigoAreaComum "
					+ "limit 1 ")
	public LocalDateTime buscaDataFimLivre(LocalDateTime dataFim, Long codigoAreaComum);

}
