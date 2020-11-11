package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.GaragemMorador;

@Repository
public interface GaragemMoradorRepository extends JpaRepository<GaragemMorador, Long>{
	
	
	@Query(nativeQuery = true, 
			value = "select count(v.vaga_garagem_id) as 'qtd_ocupada' from vagas_garagem v "
			+ "inner join setores_garagem s on s.setor_garagem_id = v.setor_garagem_id "
			+ "left join garagem_morador g on g.vaga_garagem_id = v.vaga_garagem_id "
			+ "where g.vaga_garagem_id is not null ")
	public Long qtdVagaOcupada();
	
	
	@Query(nativeQuery = true, 
			value = "select count(v.vaga_garagem_id) as 'qtd_ocupada' from vagas_garagem v "
			+ "inner join setores_garagem s on s.setor_garagem_id = v.setor_garagem_id "
			+ "left join garagem_morador g on g.vaga_garagem_id = v.vaga_garagem_id "
			+ "where g.vaga_garagem_id is null ")
	public Long qtdVagasDisponiveis();
	
	
	
	@Query(nativeQuery = true,
			value = "select count(m.morador_id) as 'qtd_pessoa_sem_vaga' from moradores m "
			+ "left join garagem_morador g on g.pessoa_id = m.pessoa_id "
			+ "where g.pessoa_id is null ")
	public Long qtdMoradoresSemVagas();

}
