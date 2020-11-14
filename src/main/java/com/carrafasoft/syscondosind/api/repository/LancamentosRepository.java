package com.carrafasoft.syscondosind.api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.syscondosind.api.model.Lancamentos;

@Repository
public interface LancamentosRepository extends JpaRepository<Lancamentos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select * from lancamentos where chave_pesquisa = :chave ")
	public List<Lancamentos> buscarPorchave(String chave);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,
	value = "delete from lancamentos where chave_pesquisa = :chave ")
	public void deletaLancPorChave(String chave);
	
	
	@Query(nativeQuery = true,
			value = "select sum(valor)  as 'Valor_a_pagar' from lancamentos "
					+ "where data_vencimento between :dataVencimentoDe and :dataVencimentoAte "
					+ "and tipo_natureza = 'A_PAGAR' "
					+ "and situacao <> 'PAGO' ")
	public BigDecimal buscaValoresAPagarPorData(LocalDate dataVencimentoDe, LocalDate dataVencimentoAte);
	
	
	@Query(nativeQuery = true,
			value = "select sum(valor)  as 'Valor_a_pagar' from lancamentos "
					+ "where data_vencimento between :dataVencimentoDe and :dataVencimentoAte "
					+ "and tipo_natureza = 'RECEBER' "
					+ "and situacao <> 'PAGO' ")
	public BigDecimal buscaValoresAReceberrPorData(LocalDate dataVencimentoDe, LocalDate dataVencimentoAte);
	


}
