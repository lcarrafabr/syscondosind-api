package com.carrafasoft.syscondosind.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Pessoas;
import com.carrafasoft.syscondosind.api.model.RetiradaDeProdutos;
import com.carrafasoft.syscondosind.api.repository.PessoaRepository;
import com.carrafasoft.syscondosind.api.repository.ProdutoRepository;
import com.carrafasoft.syscondosind.api.repository.RetiradaDeProdutoRepository;

@Service
public class RetiradaProdutosService {
	
	@Autowired
	private RetiradaDeProdutoRepository retiradaDeProdutoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	/*****/
	public ResponseEntity<?> cadastraRetiradaProduto(RetiradaDeProdutos retiradaProd, HttpServletResponse response) {
		
		Long codigoProduto = retiradaProd.getProduto().getProdutoId();
		Double qtdARetirar = retiradaProd.getQuantidade();
		Double qtdEstoqueFinal = produtoRepository.verificaQtdRetirada(codigoProduto, qtdARetirar);
		
		ResponseEntity<RetiradaDeProdutos> httpStatus = new ResponseEntity<RetiradaDeProdutos>(HttpStatus.METHOD_NOT_ALLOWED);
		ResponseEntity<String> message = null;
		Boolean operador = false;
		
		if(qtdEstoqueFinal >= 0.0) {
			
			RetiradaDeProdutos retiradaSalvaProd = retiradaDeProdutoRepository.save(retiradaProd);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, retiradaSalvaProd.getRetiradaProdutoId()));
			
			httpStatus = ResponseEntity.status(HttpStatus.CREATED).body(retiradaSalvaProd);
			operador = true;
			
		} else {
			
			message = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("O estoque não é suficiente para a quantidade retirada informada.");
		}
		
		return operador ? httpStatus : message;
	}
	
	public RetiradaDeProdutos estornoRetiradaProduto(Long codigo, HttpServletResponse response, String codigoPessoa) {
		
		RetiradaDeProdutos estorno = new RetiradaDeProdutos();
		RetiradaDeProdutos retiradaSalva = buscaPorId(codigo);
		Pessoas pessoaQueEstornou = pessoaRepository.findById(retiradaSalva.getPessoa().getPessoaId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		//Condominios condominio = condominioRepository.findById(e)
		
		Double quantidade = retiradaSalva.getQuantidade() * -1;
		
		estorno.setCondominio(retiradaSalva.getCondominio());
		estorno.setPessoa(pessoaQueEstornou);
		estorno.setProduto(retiradaSalva.getProduto());
		estorno.setDescricao(retiradaSalva.getDescricao());
		estorno.setDataRetirada(retiradaSalva.getDataRetirada());
		estorno.setQuantidade(quantidade);
		
		RetiradaDeProdutos retiradaEstornada = retiradaDeProdutoRepository.save(estorno);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, codigo));

		return retiradaEstornada;
	}
	
	private RetiradaDeProdutos buscaPorId(Long codigo) {
		
		RetiradaDeProdutos retiradaProdSalvo = retiradaDeProdutoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return retiradaProdSalvo;
	}

}
