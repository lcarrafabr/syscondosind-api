package com.carrafasoft.syscondosind.api.repository;

import java.math.BigDecimal;
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
	
	
	@Query(nativeQuery = true,
			value = "select sum(valor) / (select count(morador_id) from moradores where gerar_boleto = 1) as \"valor_a_pagar\" "
					+ "from lancamentos "
					+ "where tipo_natureza = 'A_PAGAR' "
					+ "and situacao <> 'PAGO' "
					+ "and data_vencimento between :dataIni and :dataFim ")
	BigDecimal gerarValoresCondominioAReceber(LocalDate dataIni, LocalDate dataFim);
	
	
	@Query(nativeQuery = true,
			value = "select sum(valor)  as \"valor_total_a_pagar\" "
					+ "from lancamentos "
					+ "where tipo_natureza = 'A_PAGAR' "
					+ "and situacao <> 'PAGO' "
					+ "and data_vencimento between :dataIni and :dataFim ")
	BigDecimal valorTotalAPagarPorPeriodo(LocalDate dataIni, LocalDate dataFim);

}
