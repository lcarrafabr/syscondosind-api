package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Moradores;

@Repository
public interface MoradorRepository  extends JpaRepository<Moradores, Long>{
	
	@Query(nativeQuery = true, value = "select m.* "
			+ "from moradores m "
			+ "inner join pessoas p on p.pessoa_id = m.pessoa_id "
			+ "where p.status = 1 ")
	List<Moradores> findMoradorPessoaAtiva();
	
	
	@Query(nativeQuery = true,
			value = "select count(morador_id) as qtd_morador from moradores where gerar_boleto = 1 ")
	Integer quatidadeDeMoradoresparaGerarBoleto();

}
