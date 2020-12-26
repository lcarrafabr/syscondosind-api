package com.carrafasoft.syscondosind.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.PessoaListaCompras;
import com.carrafasoft.syscondosind.api.model.SolicitacaoCompra;
import com.carrafasoft.syscondosind.api.repository.PessoaListaCompraRepository;
import com.carrafasoft.syscondosind.api.repository.SolicitacaoCompraRepository;

@Service
public class SolicitacaoCompraService {
	
	@Autowired
	private SolicitacaoCompraRepository solicitacaoCompraRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaListaCompraService pessoaListaCompraService;
	
	@Autowired
	private PessoaListaCompraRepository pessoaListaCompraRepository;
	
	
	public ResponseEntity<?> abrirSolicitacaoCompra(SolicitacaoCompra solicitacao, HttpServletResponse response) {
		
		ResponseEntity<SolicitacaoCompra> httpStatus = new ResponseEntity<SolicitacaoCompra>(HttpStatus.METHOD_NOT_ALLOWED);
		Boolean permiteAbrirSolicitacao = permiteCriarSolicitacao(solicitacao.getPessoaListaCompra().getPessoaListaCompraID());
		Boolean operador = false;
		ResponseEntity<String> message = null;
		
		if(permiteAbrirSolicitacao) {
			
			Long ultimoIDSolicitacao = solicitacaoCompraRepository.ultimoId(solicitacao.getCondominio().getCondominioId());
			solicitacao.gerarCodigoSolicitacao(ultimoIDSolicitacao);

			SolicitacaoCompra solicitacaoSalva = solicitacaoCompraRepository.save(solicitacao);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, solicitacaoSalva.getSolicitacaoCompraID()));
			
			httpStatus = ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoSalva);
			operador = true;
			
		} else {
			
			message = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("O usuário não tem permissão para abrir solicitações de compra.");
		}

		return operador ? httpStatus : message;
	}
	
	public ResponseEntity<?> cancelarAberturaDeSolicitacaoDeCompra(Long codigo, String pessoaAprovador) {
		
		SolicitacaoCompra solicitacaoSalva = new SolicitacaoCompra();
		PessoaListaCompras pessoaListSalva = pessoaListaCompraRepository.buscaPorCodigoPessoa(Long.parseLong(pessoaAprovador));
		Object verificaClasse = pessoaListSalva;
		Boolean permiteCriarSolicitacao = false;
		
		if(verificaClasse != null) {
			
			permiteCriarSolicitacao = permiteCriarSolicitacao(pessoaListSalva.getPessoaListaCompraID());
		}
		
		
		ResponseEntity<SolicitacaoCompra> httpStatus = new ResponseEntity<SolicitacaoCompra>(HttpStatus.METHOD_NOT_ALLOWED);
		ResponseEntity<String> message = null;
		Boolean operador = false;
		
		if(permiteCriarSolicitacao) {
			
			solicitacaoSalva = buscaPorID(codigo);
			solicitacaoSalva.cancelarSolicitacao();
			solicitacaoCompraRepository.save(solicitacaoSalva);
			
			httpStatus = ResponseEntity.ok(solicitacaoSalva);
			operador = true;
		
		} else {
			
			message = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("O usuário não tem permissão para abrir solicitações de compra.");
		}
		
		
		return operador ? httpStatus : message;
		
	}

	private Boolean permiteCriarSolicitacao(Long codigoPessoaLista) {
		
		PessoaListaCompras pessoaLista = pessoaListaCompraService.buscaPorId(codigoPessoaLista);
		Boolean permiteAbrirSolicitacao = pessoaLista.getPermiteCriarSolicitacao();
		return permiteAbrirSolicitacao;
	}
	
	private SolicitacaoCompra buscaPorID(Long codigo) {
		
		SolicitacaoCompra solicitacaoSalva = solicitacaoCompraRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return solicitacaoSalva;
	}

}
