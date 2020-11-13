package com.carrafasoft.syscondosind.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.LancamentosRepository;

@Service
public class LancamentosService {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public List<Lancamentos> cadastrarLancamentosParcelado(Lancamentos lancamentos, HttpServletResponse response) {
		
		Lancamentos preparaSalvar = new Lancamentos();
		preparaSalvar = lancamentos;
		
		Integer qtdParcela = lancamentos.getQuantidadeParcelas();		
		LocalDate dataPrimeiroVencimento = lancamentos.getDataVencimento();
		BigDecimal valorTotal = lancamentos.getValor();
		//BigDecimal valorParcelado = valorTotal.divide(new BigDecimal(qtdParcela).setScale(2, RoundingMode.HALF_EVEN));
		
		BigDecimal qtd = new BigDecimal(qtdParcela);
		
		
		BigDecimal valorParcelado = valorTotal.divide(qtd, 2);
		BigDecimal ValorTotalparcelas = valorParcelado.multiply(qtd);
		BigDecimal valorRestante = ValorTotalparcelas.subtract(valorTotal);
		
		//System.out.println("Quantidade parcelas: " + qtdParcela);
		
		List<Lancamentos> list = new ArrayList<Lancamentos>();
		
		for (int i = 0; i < qtdParcela; i++) {
			
			
			LocalDate dataPrimeiroVencimento2 = dataPrimeiroVencimento.plusMonths(i);
			//System.out.println(i);
			lancamentos.setNumeroParcela(i+1);
			lancamentos.setDataVencimento(dataPrimeiroVencimento2);
			
			if(valorRestante.compareTo(BigDecimal.ZERO) != 0 && lancamentos.getNumeroParcela() == 1) {
				System.out.println("Primeira parcela");
				
				BigDecimal valorPrimeiraPacela = valorParcelado.subtract(valorRestante);
				lancamentos.setValor(valorPrimeiraPacela);
			
			} else {
				
				lancamentos.setValor(valorParcelado);
			}
			
			System.out.println(lancamentos.getDataVencimento());
			System.out.println("Numero parcela: " + lancamentos.getNumeroParcela() + "/" + qtdParcela);
			System.out.println("Valor parcelado: " + lancamentos.getValor());
			System.out.println("Valor total parcelado: " + ValorTotalparcelas);
			System.out.println("Valor a descontar na 1° parcela: "+ valorRestante);
			System.out.println("-------------------------------------------------------------------------");
			
			list.add(lancamentos);
			
			
//			Lancamentos lancamentoSalvo = lancamentosRepository.save(lancamentos);
//			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentos.getLancamentoId()));
//			
//			System.out.println(lancamentos.getLancamentoId());
//			
//			ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	
			
		}
		
		List<Lancamentos> lancSalvos = lancamentosRepository.saveAll(list);

		
		return lancSalvos;
	}
	

}
