package com.carrafasoft.syscondosind.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.syscondosind.api.enums.StatusSituacao;
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.LancamentosRepository;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

@Service
public class LancamentosService {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	@Transactional
	public Lancamentos cadastrarLancamentosParcelado(Lancamentos lancamentos, HttpServletResponse response, String chavePesquisa) {

		Integer qtdParcela = lancamentos.getQuantidadeParcelas();		
		LocalDate dataPrimeiroVencimento = lancamentos.getDataVencimento();
		BigDecimal valorTotal = lancamentos.getValor();
		
		BigDecimal qtd = new BigDecimal(qtdParcela);
		
		
		BigDecimal valorParcelado = valorTotal.divide(qtd, 2);
		BigDecimal ValorTotalparcelas = valorParcelado.multiply(qtd);
		BigDecimal valorRestante = ValorTotalparcelas.subtract(valorTotal);
		
		//System.out.println("Quantidade parcelas: " + qtdParcela);
		Lancamentos lancamentoSalvo2 = new Lancamentos();
		
		for (int i = 0; i < qtdParcela; i++) {
			
			Lancamentos preparaSalvar = new Lancamentos();
					
			LocalDate dataPrimeiroVencimento2 = dataPrimeiroVencimento.plusMonths(i);
			//System.out.println(i);
			lancamentos.setNumeroParcela(i+1);
			lancamentos.setDataVencimento(dataPrimeiroVencimento2);
			
			if(valorRestante.compareTo(BigDecimal.ZERO) != 0 && lancamentos.getNumeroParcela() == 1) {
				//System.out.println("Primeira parcela");
				BigDecimal valorPrimeiraPacela = valorParcelado.subtract(valorRestante);
				lancamentos.setValor(valorPrimeiraPacela);
				preparaSalvar.setValor(valorPrimeiraPacela);
			
			} else {
				
				lancamentos.setValor(valorParcelado);
				preparaSalvar.setValor(valorParcelado);
			}			
//			System.out.println(lancamentos.getDataVencimento());
//			System.out.println("Numero parcela: " + lancamentos.getNumeroParcela() + "/" + qtdParcela);
//			System.out.println("Valor parcelado: " + lancamentos.getValor());
//			System.out.println("Valor total parcelado: " + ValorTotalparcelas);
//			System.out.println("Valor a descontar na 1° parcela: "+ valorRestante);
//			System.out.println("-------------------------------------------------------------------------");
			
			preparaSalvar.setTipoNatureza(lancamentos.getTipoNatureza());
			preparaSalvar.setDataVencimento(dataPrimeiroVencimento2);
			preparaSalvar.setDataPagamento(lancamentos.getDataPagamento());
			preparaSalvar.setDescricao(lancamentos.getDescricao());
			//preparaSalvar.setSituacao(lancamentos.getSituacao());
			preparaSalvar.setParcelado(lancamentos.getParcelado());
			preparaSalvar.setQuantidadeParcelas(lancamentos.getQuantidadeParcelas());
			preparaSalvar.setNumeroParcela(i+1);
			preparaSalvar.setFormaPagamento(lancamentos.getFormaPagamento());
			preparaSalvar.setContaBancaria(lancamentos.getContaBancaria());
			preparaSalvar.setCentroCusto(lancamentos.getCentroCusto());
			preparaSalvar.setCategoriaConta(lancamentos.getCategoriaConta());
			preparaSalvar.setChavePesquisa(chavePesquisa);
			
			lancamentoSalvo2 = lancamentosRepository.save(preparaSalvar);		
		}		
		return lancamentoSalvo2;
	}
	
	public ResponseEntity<Object> removerLancamentoSemParcela(Long codigo) {
		
		Lancamentos lancSalvo = buscaPorId(codigo);
		
		ResponseEntity<Object> retorno = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		
		if(!lancSalvo.getParcelado().equals(true)) {
			
			lancamentosRepository.deleteById(codigo);
			
			retorno = ResponseEntity.noContent().build();
		
		} else {
			
			retorno = ResponseEntity.badRequest().build();
		}
		return retorno;
	}
	
	public Lancamentos atualizaLancamento(Long codigo, Lancamentos lancamento) {
		
		Lancamentos lancSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(lancamento, lancSalvo, "lancamentoId");
		
		return lancamentosRepository.save(lancSalvo);
	}
	
	
	public Lancamentos cadastrarLancamentoQuandoAlugarAreaComum(Lancamentos lancamento) {
		
		String chavePesquisa = FuncoesUtils.gerarHash();
		
		List<Lancamentos> verificaLanc = lancamentosRepository.buscarPorchave(chavePesquisa);
		
		 while(!verificaLanc.isEmpty()) {
			 
			 chavePesquisa = FuncoesUtils.gerarHash();
			 verificaLanc = lancamentosRepository.buscarPorchave(chavePesquisa);
		 }
		 
		 lancamento.setChavePesquisa(chavePesquisa);
		
		return lancamentosRepository.save(lancamento);
		
	}
	
	
	public void atualizaStatusLancamento(String chavePesquisa) {
		
		List<Lancamentos> lancSalvo = lancamentosRepository.buscarPorchave(chavePesquisa);
		
		
		lancSalvo.stream().forEach((list) -> {
			Lancamentos removeLanc = new Lancamentos();
			
			removeLanc.setLancamentoId(list.getLancamentoId());
			removeLanc.setTipoNatureza(list.getTipoNatureza());
			removeLanc.setDataPagamento(list.getDataPagamento());
			removeLanc.setDataVencimento(list.getDataVencimento());
			removeLanc.setDescricao(list.getDescricao());			
			removeLanc.setSituacao(StatusSituacao.CANCELADO);
			removeLanc.setParcelado(list.getParcelado());
			removeLanc.setQuantidadeParcelas(list.getQuantidadeParcelas());
			removeLanc.setNumeroParcela(list.getNumeroParcela());
			removeLanc.setFormaPagamento(list.getFormaPagamento());
			removeLanc.setContaBancaria(list.getContaBancaria());
			removeLanc.setCentroCusto(list.getCentroCusto());
			removeLanc.setCategoriaConta(list.getCategoriaConta());
			removeLanc.setChavePesquisa(list.getChavePesquisa());
			removeLanc.setValor(list.getValor());
			
			lancamentosRepository.save(removeLanc);
		});
		
		//lancamentosRepository.saveAll(lancSalvo);
	}
	
	
	private Lancamentos buscaPorId(Long codigo) {
		
		Lancamentos lancSalvo = lancamentosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return lancSalvo;
	}
	

}
