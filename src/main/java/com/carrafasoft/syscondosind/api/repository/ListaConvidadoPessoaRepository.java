package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.ListaConvidadosPessoas;

@Repository
public interface ListaConvidadoPessoaRepository extends JpaRepository<ListaConvidadosPessoas, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from lista_convidado_pessoas where lista_id = :codigo ")
	public List<ListaConvidadosPessoas> buscaPorLista(Long codigo);
	

}
