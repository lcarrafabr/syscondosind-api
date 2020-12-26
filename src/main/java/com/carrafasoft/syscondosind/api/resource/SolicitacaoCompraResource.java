package com.carrafasoft.syscondosind.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.SolicitacaoCompra;
import com.carrafasoft.syscondosind.api.repository.SolicitacaoCompraRepository;
import com.carrafasoft.syscondosind.api.service.SolicitacaoCompraService;

@RestController
@RequestMapping("/solicitacao-de-compras")
public class SolicitacaoCompraResource {
	
	@Autowired
	private SolicitacaoCompraRepository solicitacaoCompraRepository;
	
	
	
	@Autowired
	private SolicitacaoCompraService solicitacaoCompraService;
	
	
	@GetMapping
	public List<SolicitacaoCompra> listarTodos() {
		
		return solicitacaoCompraRepository.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<?> cadastrarSolicitacao(@Valid @RequestBody SolicitacaoCompra solicitacao, HttpServletResponse response) {
		
		ResponseEntity<?> retornoResponse = solicitacaoCompraService.abrirSolicitacaoCompra(solicitacao, response);
		
		
		return retornoResponse;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<SolicitacaoCompra> buscaPorId(@PathVariable Long codigo) {
		
		Optional<SolicitacaoCompra> solicitacaoSalva = solicitacaoCompraRepository.findById(codigo);
		
		return solicitacaoSalva.isPresent() ? ResponseEntity.ok(solicitacaoSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{codigo}/cancelar")
	public ResponseEntity<?> cancelarSolicitacaoDeCompra(@PathVariable Long codigo, @RequestParam("pessoaID") String pessoaID) {
		
		ResponseEntity<?> retorno = solicitacaoCompraService.cancelarAberturaDeSolicitacaoDeCompra(codigo, pessoaID);
		
		return retorno;
	}

}
