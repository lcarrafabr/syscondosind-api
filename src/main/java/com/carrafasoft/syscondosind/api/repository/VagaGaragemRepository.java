package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.VagasGaragem;

@Repository
public interface VagaGaragemRepository extends JpaRepository<VagasGaragem, Long>{
	
	@Query(nativeQuery = true, value = "select s.nome_setor, count(v.vaga_garagem_id) as 'qtd_vaga' "
			+ "from vagas_garagem v "
			+ "inner join setores_garagem s on s.setor_garagem_id = v.setor_garagem_id "
			+ "group by v.setor_garagem_id ")
	public List<Object> totalVagasPorSetor();

}
